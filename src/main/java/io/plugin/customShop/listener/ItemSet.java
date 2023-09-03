package io.plugin.customShop.listener;

import io.plugin.customShop.inventory.InventoryManager;
import org.bukkit.ChatColor;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

import static io.plugin.customShop.Main.plugin;
import static io.plugin.customShop.Main.title;

public class ItemSet implements Listener {

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
