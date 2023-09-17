package io.plugin.customShop.listener;

import io.plugin.customShop.Main;
import io.plugin.customShop.inventory.InventoryManager;
import io.plugin.customShop.utils.Color;
import io.plugin.customShop.utils.ItemBuild;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static io.plugin.customShop.Main.plugin;
import static io.plugin.customShop.Main.title;

public class ShopMainCenter {

    public static Map<String, String> changeEcoSetting = new HashMap<>();

    public static void registerListeners(Plugin plugin) {
        Bukkit.getPluginManager().registerEvents(new ServiceItemPriceSetting(), plugin);
        Bukkit.getPluginManager().registerEvents(new ServiceItemSettingInvOpen(), plugin);
        Bukkit.getPluginManager().registerEvents(new ServicePriceSetting(), plugin);
        Bukkit.getPluginManager().registerEvents(new ServiceRegistrationItem(), plugin);
        Bukkit.getPluginManager().registerEvents(new ServiceSaveShopSettingsItem(), plugin);
        Bukkit.getPluginManager().registerEvents(new ServiceShopLineEdit(), plugin);
        Bukkit.getPluginManager().registerEvents(new ServiceChangeEconomy(), plugin);
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
                        InventoryManager.openInventory(player, size, getShopName, "상점 구매&판매설정");
                        return;
                    }
                }
            }
        }
    }
}

class ServiceItemSettingInvOpen implements Listener {

    public static Map<String, Integer> saveSlot = new HashMap<>();

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
                    saveSlot.put("saveSlot", event.getSlot());
                    InventoryManager.itemFix(player);
                    return;
                }
            }
        }
    }
}

class ServicePriceSetting implements Listener {

    public ServicePriceSetting() {}

    //가격설정을 위한 설정창에서 아이템 클릭
    @EventHandler
    public void priceSettingClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        if (!event.getView().getTitle().equals("가격설정")) return;
        if (Objects.equals(event.getCurrentItem(), ItemBuild.priceSet())) {
            player.closeInventory();
            player.sendMessage(title + "채팅에 가격을 입력 해주세요.");
            Bukkit.getPluginManager().registerEvents(new ServicePriceSetting(), Main.getPlugin());
        }
    }

    //채팅으로 가격입력
    @EventHandler
    public void onChat(PlayerChatEvent event) {
        Player getMessagePlayer = event.getPlayer();
        String message = event.getMessage();

        if (!message.isEmpty() || !isNumeric(message)) {

            boolean found = false;
            ConfigurationSection configSection = plugin.getConfig().getConfigurationSection("상점목록");
            for (String list : configSection.getKeys(false)) {
                String getShopName = plugin.getConfig().getString("상점목록." + list);
                plugin.getConfig().set(getShopName + "." + ServiceItemSettingInvOpen.saveSlot.get("saveSlot") + ".buy", message);
                plugin.saveConfig();
                found = true;
                break; // 한 번 찾았으면 반복문 종료
            }

            if (found) {
                InventoryManager.itemFix(getMessagePlayer);
                getMessagePlayer.sendMessage(title + "가격설정이 완료되었습니다!");
            }
            HandlerList.unregisterAll(this); // 채팅을 계속 입력받는 거 방지 (인벤클릭 이벤트도 이벤트에서 해제됨 수정..)
            event.setCancelled(true); // 채팅 보내기 취소
        }
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
                    InventoryManager.openInventory(player, size, getShopName, "");
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

class ServiceChangeEconomy implements Listener {

    @EventHandler
    public void changeCashOrMoney(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        if (!event.getView().getTitle().equals("가격설정")) return;
        if (event.getSlot() == 10) {
            ShopMainCenter.changeEcoSetting.putIfAbsent("changeEcoSetting", "Cash");
            if (ShopMainCenter.changeEcoSetting.get("changeEcoSetting").equals("Cash")) {
                ShopMainCenter.changeEcoSetting.put("changeEcoSetting", "Money");
                player.sendMessage(title + Color.chat("&6Money&f로 변경되었습니다."));
            } else if (ShopMainCenter.changeEcoSetting.get("changeEcoSetting").equals("Money")) {
                ShopMainCenter.changeEcoSetting.put("changeEcoSetting", "Cash");
                player.sendMessage(title + Color.chat("&aCash&f로 변경되었습니다."));
            }
        }
    }
}