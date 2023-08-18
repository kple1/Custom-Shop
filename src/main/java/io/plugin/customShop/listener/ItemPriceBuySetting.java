package io.plugin.customShop.listener;

import io.plugin.customShop.inventory.OpenInventory;
import org.bukkit.ChatColor;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

import static io.plugin.customShop.Main.plugin;

public class ItemPriceBuySetting implements Listener {

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
                        OpenInventory openInventory = new OpenInventory();
                        openInventory.openInventoryToItemSet(player, size, getShopName);
                        return;
                    }
                }
            }
        }
    }
}
