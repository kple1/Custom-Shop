package io.plugin.customShop.listener;

import io.plugin.customShop.inventory.OpenInventory;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

import static io.plugin.customShop.Main.plugin;

public class ItemSettingOpen implements Listener {

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

                    OpenInventory openInventory = new OpenInventory();
                    openInventory.itemFix(player);
                    return;
                }
            }
        }
    }
}
