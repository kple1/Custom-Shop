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
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static io.plugin.customShop.Main.plugin;
import static io.plugin.customShop.Main.title;
import static io.plugin.customShop.config.ConfigSection.configSection;
import static io.plugin.customShop.utils.ItemBuild.*;

public class ShopMainCenter {

    public static Map<String, String> changeEcoSetting = new HashMap<>();
    public static Map<String, String> changeSellOrBuySetting = new HashMap<>();

    public static void registerListeners(Plugin plugin) {
        Bukkit.getPluginManager().registerEvents(new ServiceItemPriceSetting(), plugin);
        Bukkit.getPluginManager().registerEvents(new ServiceItemSettingInvOpen(), plugin);
        Bukkit.getPluginManager().registerEvents(new ServicePriceInputChat(), plugin);
        Bukkit.getPluginManager().registerEvents(new ServiceWaitingForSalesInput(), plugin);
        Bukkit.getPluginManager().registerEvents(new ServiceRegistrationItem(), plugin);
        Bukkit.getPluginManager().registerEvents(new ServiceSaveShopSettingsItem(), plugin);
        Bukkit.getPluginManager().registerEvents(new ServiceShopLineEdit(), plugin);
        Bukkit.getPluginManager().registerEvents(new ServiceChangeEconomy(), plugin);
        Bukkit.getPluginManager().registerEvents(new ServiceChangeSellOrBuySetting(), plugin);
    }
}

class ServiceItemPriceSetting implements Listener {

    @EventHandler
    public void itemPriceAndBuySettings(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();

        for (String list : configSection("상점목록").getKeys(false)) {

            String getShopName = plugin.getConfig().getString("상점목록." + list);
            int size = plugin.getConfig().getInt(getShopName + ".size");

            if (ChatColor.stripColor(event.getView().getTitle()).equalsIgnoreCase(getShopName + "상점 편집메뉴")) {

                if (event.getSlot() == 16) {
                    InventoryManager.openInventory(player, size, getShopName, "상점 구매&판매설정");
                    return;
                }
            }
        }
    }
}

class ServiceItemSettingInvOpen implements Listener {

    public static Map<String, Integer> saveSlot = new HashMap<>();

    @EventHandler
    public void itemPriceClickToOpenSetting(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();

        for (String list : configSection("상점목록").getKeys(false)) {

            String getShopName = plugin.getConfig().getString("상점목록." + list);
            if (event.getView().getTitle().equals(getShopName + "상점 구매&판매설정")) {

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
}

class ServiceWaitingForSalesInput implements Listener {
    @EventHandler
    public void priceSettingClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        if (!event.getView().getTitle().equals("가격설정")) return;
        if (Objects.equals(event.getCurrentItem(), ItemBuild.priceSet())) {
            player.closeInventory();
            player.sendMessage(title + "채팅에 가격을 입력 해주세요.");
            Bukkit.getPluginManager().registerEvents(new ServicePriceInputChat(), Main.getPlugin());
        }
    }
}

class ServicePriceInputChat implements Listener {

    public ServicePriceInputChat() {
    }

    //채팅으로 가격입력
    @EventHandler
    public void onChat(PlayerChatEvent event) {
        Player getMessagePlayer = event.getPlayer();
        String message = event.getMessage();

        if (!message.isEmpty() || !isNumeric(message)) {

            boolean found = false;
            for (String list : configSection("상점목록").getKeys(false)) {
                String getShopName = plugin.getConfig().getString("상점목록." + list);
                plugin.getConfig().set(getShopName + "." + ServiceItemSettingInvOpen.saveSlot.get("saveSlot") + "." + ShopMainCenter.changeSellOrBuySetting.get("changePriceSetting"), message);

                //addLore Logic 구현

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

        for (String list : configSection("상점목록").getKeys(false)) {
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

        for (String list : configSection("상점목록").getKeys(false)) {
            String getShopName = plugin.getConfig().getString("상점목록." + list);

            if (event.getView().getTitle().equals(getShopName)) {
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
            }
        }
    }
}

class ServiceShopLineEdit implements Listener {

    //상점 줄 설정 우클릭시 감소 좌클릭시 증가
    @EventHandler
    public void shopLineEdit(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();

        for (String list : configSection("상점목록").getKeys(false)) {

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

            ItemStack updatedItem = ecoItem();
            player.getOpenInventory().setItem(10, updatedItem);
        }
    }
}

class ServiceChangeSellOrBuySetting implements Listener {

    @EventHandler
    public void changePriceSetting(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        if (!event.getView().getTitle().equals("가격설정")) return;
        if (event.getSlot() == 13) {
            ShopMainCenter.changeSellOrBuySetting.putIfAbsent("changePriceSetting", "buy");
            if (ShopMainCenter.changeSellOrBuySetting.get("changePriceSetting").equals("buy")) {
                ShopMainCenter.changeSellOrBuySetting.put("changePriceSetting", "sell");
                player.sendMessage(title + Color.chat("&bsell&f로 변경되었습니다."));
            } else if (ShopMainCenter.changeSellOrBuySetting.get("changePriceSetting").equals("sell")) {
                ShopMainCenter.changeSellOrBuySetting.put("changePriceSetting", "buy");
                player.sendMessage(title + Color.chat("&abuy&f로 변경되었습니다."));
            }
            // 클릭한 후 아이템 이름과 설명을 업데이트
            ItemStack updatedItem = sellOrBuySetting();
            player.getOpenInventory().setItem(13, updatedItem);
        }
    }
}

class MainUserShop implements Listener {

    @EventHandler
    public void mainUserShopItemClick(InventoryClickEvent event) {
        //TODO
        //아이템의 Lore를 가져오는 방식으로 운영
        //Lore에 Cash, Money로 구별
        //Lore에 돈 구별
    }
}
