package io.plugin.customShop.listener;

import org.bukkit.ChatColor;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

import static io.plugin.customShop.Main.plugin;

public class InvClickCancel implements Listener {

    @EventHandler
    public void cancelEvent(InventoryClickEvent event) {
        if (event.getClickedInventory() == null) return;

        ConfigurationSection shopList = plugin.getConfig().getConfigurationSection("상점목록");
        for (String list : shopList.getKeys(false)) {
            String getShopName = plugin.getConfig().getString("상점목록." + list);
            if (ChatColor.stripColor(event.getView().getTitle()).equalsIgnoreCase(getShopName + "상점 편집메뉴")) {
                event.setCancelled(true);
                break;
            }

            if (ChatColor.stripColor(event.getView().getTitle()).equalsIgnoreCase(getShopName + "상점")) {
                event.setCancelled(true);
                break;
            }

            if (ChatColor.stripColor(event.getView().getTitle()).equalsIgnoreCase(getShopName + "상점 구매&판매설정")) {
                event.setCancelled(true);
                break;
            }

            if (ChatColor.stripColor(event.getView().getTitle()).equalsIgnoreCase("가격설정")) {
                event.setCancelled(true);
                break;
            }
        }
    }
}
