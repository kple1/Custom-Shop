package io.plugin.customShop.listeners;

import io.plugin.customShop.utils.Color;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

import static io.plugin.customShop.Main.plugin;
import static io.plugin.customShop.Main.title;
import static io.plugin.customShop.config.ConfigSection.configSection;

public class ServiceShopLineEdit implements Listener {

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
