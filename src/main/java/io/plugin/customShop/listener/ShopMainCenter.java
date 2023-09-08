package io.plugin.customShop.listener;

import io.plugin.customShop.Main;
import io.plugin.customShop.config.UserConfig;
import io.plugin.customShop.inventory.InventoryManager;
import io.plugin.customShop.utils.Color;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.UUID;

import static io.plugin.customShop.Main.plugin;
import static io.plugin.customShop.Main.title;
import static io.plugin.customShop.utils.CashFunction.cashAdd;
import static io.plugin.customShop.utils.CashFunction.getCash;

public class ShopMainCenter {
    public static void registerListeners() {
        Bukkit.getPluginManager().registerEvents(new ServiceGetCash(), Main.getPlugin());
        Bukkit.getPluginManager().registerEvents(new ServiceItemPriceSetting(), Main.getPlugin());
        Bukkit.getPluginManager().registerEvents(new ServiceItemSettingInvOpen(), Main.getPlugin());
        Bukkit.getPluginManager().registerEvents(new ServicePriceSetting(), Main.getPlugin());
        Bukkit.getPluginManager().registerEvents(new ServiceRegistrationItem(), Main.getPlugin());
        Bukkit.getPluginManager().registerEvents(new ServiceSaveShopSettingsItem(), Main.getPlugin());
        Bukkit.getPluginManager().registerEvents(new ServiceShopLineEdit(), Main.getPlugin());
    }
}

class ServiceGetCash implements Listener {

    @EventHandler
    public void cashSet(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        UUID playerUUID = player.getUniqueId();

        ItemStack itemInHand = player.getInventory().getItemInMainHand();
        ItemMeta itemMeta = itemInHand.getItemMeta();

        if (!(event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK)) return;
        if (!itemMeta.hasDisplayName() || !itemMeta.hasLore()) return;

        String key = itemMeta.getDisplayName();
        String extractedNumbers = key.replaceAll("\\D", "");

        if (!extractedNumbers.isEmpty()) {
            int cashValue = Integer.parseInt(extractedNumbers); //추가할 돈 가져오기
            Main.getPlugin().removeItemsFromMainHand(player, 1); //사용한 아이템 삭제

            cashAdd(playerUUID, cashValue); //돈 추가
            player.sendMessage(title + Color.chat("캐쉬가 발급 되었습니다!"));
            player.sendMessage(title + Color.chat("현재 잔액: " + getCash(playerUUID))); // 플레이어의 현재 돈 출력
        }
    }
}

class ServiceItemPriceSetting implements Listener {

    @EventHandler
    public void itemPriceAndBuySettings(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();

        ConfigurationSection configSection = plugin.getConfig().getConfigurationSection("상점목록");
        for (String list : configSection.getKeys(false)) {

            String getShopName = plugin.getConfig().getString("상점목록." + list);
            int size = plugin.getConfig().getInt(getShopName + ".size");

            if (ChatColor.stripColor(event.getView().getTitle()).equalsIgnoreCase(getShopName + "상점 편집메뉴")) {

                if (event.getSlot() == 16) {
                    if (event.getClick().isLeftClick()) {
                        InventoryManager.openInventory(player, size, getShopName + "상점 구매&판매설정");
                        return;
                    }
                }
            }
        }
    }
}

class ServiceItemSettingInvOpen implements Listener {

    @EventHandler
    public void itemSellPriceItemClickToOpenSetting(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        ConfigurationSection configSection = plugin.getConfig().getConfigurationSection("상점목록");

        for (String list : configSection.getKeys(false)) {

            String getShopName = plugin.getConfig().getString("상점목록." + list);
            if (!event.getView().getTitle().equals(getShopName + "상점 구매&판매설정")) return;

            if (event.getCurrentItem() == null) return;
            if (event.getCurrentItem().getType() == Material.AIR) return;
            if (event.getClickedInventory() == null) return;
            if (event.getClickedInventory().equals(player.getInventory())) return;
            if (event.getRawSlot() >= event.getInventory().getSize()) return;

            for (int i = 0; i < event.getInventory().getSize(); i++) {
                if (event.getSlot() == i) {
                    plugin.getConfig().set("saveSlot", event.getSlot());
                    plugin.saveConfig();

                    InventoryManager.itemFix(player);
                    return;
                }
            }
        }
    }
}

class ServicePriceSetting implements Listener {

    public ServicePriceSetting() {
    }

    //가격설정을 위한 설정창에서 아이템 클릭
    @EventHandler
    public void priceSettingClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        if (event.getView().getTitle().equals("가격설정")) {
            if (event.getSlot() == 16) {
                player.closeInventory();
                player.sendMessage(title + "채팅에 가격을 입력 해주세요.");
                Bukkit.getPluginManager().registerEvents(new ServicePriceSetting(), plugin);
            }
        }
    }

    //채팅으로 가격입력
    @EventHandler
    public void onChat(PlayerChatEvent event) {
        Player getMessagePlayer = event.getPlayer();
        String message = event.getMessage();
        YamlConfiguration config = UserConfig.getPlayerConfig(getMessagePlayer);

        if (!message.isEmpty()) {
            if (!isNumeric(message)) {
                return;
            }
            ConfigurationSection configSection = plugin.getConfig().getConfigurationSection("상점목록");
            for (String list : configSection.getKeys(false)) {
                int slot = config.getInt("saveSlot");
                String getShopName = plugin.getConfig().getString("상점목록." + list);

                plugin.getConfig().set(getShopName + "." + slot + ".buy", message);
                plugin.saveConfig();
                getMessagePlayer.sendMessage(title + "가격설정이 완료되었습니다!");

                InventoryManager.itemFix(getMessagePlayer);
                event.setCancelled(true);
                break;
            }
        }
        HandlerList.unregisterAll(this);
        event.setCancelled(true);
    }

    private boolean isNumeric(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}

class ServiceRegistrationItem implements Listener {

    @EventHandler
    public void itemEdit(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();

        ConfigurationSection configSection = plugin.getConfig().getConfigurationSection("상점목록");
        for (String list : configSection.getKeys(false)) {

            String getShopName = plugin.getConfig().getString("상점목록." + list);
            int size = plugin.getConfig().getInt(getShopName + ".size");

            if (ChatColor.stripColor(event.getView().getTitle()).equalsIgnoreCase(getShopName + "상점 편집메뉴")) {
                if (event.getSlot() == 13 && event.getClick().isLeftClick()) {
                    InventoryManager.openInventory(player, size, getShopName);
                    player.sendMessage(title + "아이템 설정 창이 오픈되었습니다!");
                    return;
                }
            }
        }
    }
}

class ServiceSaveShopSettingsItem implements Listener {

    @EventHandler
    public void itemSaveForItemSet(InventoryCloseEvent event) {
        Player player = (Player) event.getPlayer();

        ConfigurationSection configSection = plugin.getConfig().getConfigurationSection("상점목록");
        for (String list : configSection.getKeys(false)) {

            String getShopName = plugin.getConfig().getString("상점목록." + list);
            if (!event.getView().getTitle().equals(getShopName)) return;

            for (int j = 0; j < event.getInventory().getSize(); j++) {
                ItemStack item = event.getView().getItem(j);
                if (item != null) {
                    plugin.getConfig().set(getShopName + "." + j + ".item", item);
                } else {
                    plugin.getConfig().set(getShopName + "." + j, null);
                }
            }
            player.sendMessage(title + "설정이 저장되었습니다.");
            plugin.saveConfig();
            break;
        }
    }
}

class ServiceShopLineEdit implements Listener {

    //상점 줄 설정 우클릭시 감소 좌클릭시 증가
    @EventHandler
    public void shopLineEdit(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();

        ConfigurationSection configSection = plugin.getConfig().getConfigurationSection("상점목록");
        for (String list : configSection.getKeys(false)) {

            String getShopName = plugin.getConfig().getString("상점목록." + list);
            int getSize = plugin.getConfig().getInt(getShopName + ".size");

            if (ChatColor.stripColor(event.getView().getTitle()).equalsIgnoreCase(getShopName + "상점 편집메뉴")) {
                if (event.getSlot() == 10) {
                    if (event.getClick().isLeftClick()) {
                        if (getSize >= 54) {
                            player.sendMessage(title + Color.chat("&c&l최대 줄&f에 도달하였습니다!"));
                            return;
                        }
                        plugin.getConfig().set(getShopName + ".size", getSize + 9);
                        plugin.saveConfig();

                        int displayValue = (getSize + 9) / 9;
                        player.sendMessage(title + displayValue + "줄로 변경되었습니다!");
                        return;
                    }

                    if (event.getClick().isRightClick()) {
                        if (getSize <= 9) {
                            player.sendMessage(title + Color.chat("&c&l최소 줄&f에 도달하였습니다!"));
                            return;
                        }
                        plugin.getConfig().set(getShopName + ".size", getSize - 9);
                        plugin.saveConfig();

                        int displayValue = (getSize - 9) / 9;
                        player.sendMessage(title + displayValue + "줄로 변경되었습니다!");
                        return;
                    }
                }
            }
        }
    }
}