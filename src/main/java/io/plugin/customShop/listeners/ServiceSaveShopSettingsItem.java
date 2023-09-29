package io.plugin.customShop.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.ItemStack;

import static io.plugin.customShop.Main.plugin;
import static io.plugin.customShop.Main.title;
import static io.plugin.customShop.config.ConfigSection.configSection;

public class ServiceSaveShopSettingsItem implements Listener {

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
