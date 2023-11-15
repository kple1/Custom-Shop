package io.plugin.customShop.listeners;

import io.plugin.customShop.utils.Color;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import static io.plugin.customShop.Main.title;
import static io.plugin.customShop.utils.ItemBuild.ecoItem;

public class ServiceChangeEconomy implements Listener {

    private final String ecoSetting = ShopMainCenter.changeEcoSetting.get("changeEcoSetting");

    @EventHandler
    public void changeCashOrMoney(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();

        if (!event.getView().getTitle().equals("가격설정")) return;
        if (event.getSlot() == 10) {
            ShopMainCenter.changeEcoSetting.putIfAbsent("changeEcoSetting", "Cash");
            if (ecoSetting.equals("Cash")) {
                put("changeEcoSetting", "Money");
                player.sendMessage(title + Color.chat("&6Money&f로 변경되었습니다."));
            } else if (ecoSetting.equals("Money")) {
                put("changeEcoSetting", "Cash");
                player.sendMessage(title + Color.chat("&aCash&f로 변경되었습니다."));
            }

            ItemStack updatedItem = ecoItem();
            player.getOpenInventory().setItem(10, updatedItem);
        }
    }

    public static void put(String key, String value) {
        ShopMainCenter.changeEcoSetting.put(key, value);
    }
}
