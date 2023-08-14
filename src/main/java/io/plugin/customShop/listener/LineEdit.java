package io.plugin.customShop.listener;

import io.plugin.customShop.utils.Color;
import org.bukkit.ChatColor;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

import static io.plugin.customShop.Main.plugin;
import static io.plugin.customShop.Main.title;

public class LineEdit implements Listener {

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
