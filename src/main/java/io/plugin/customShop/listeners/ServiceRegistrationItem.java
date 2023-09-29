package io.plugin.customShop.listeners;

import io.plugin.customShop.inventory.InventoryManager;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

import static io.plugin.customShop.Main.plugin;
import static io.plugin.customShop.Main.title;
import static io.plugin.customShop.config.ConfigSection.configSection;

public class ServiceRegistrationItem implements Listener {

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
