package io.plugin.customShop.listener;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.ItemStack;

import static io.plugin.customShop.Main.plugin;
import static io.plugin.customShop.Main.title;

public class ItemSaveForItemSet implements Listener {

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
