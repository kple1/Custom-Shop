package io.plugin.customShop.listener;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

import static io.plugin.customShop.Main.plugin;
import static io.plugin.customShop.Main.title;

public class PriceSettingClick implements Listener {

    @EventHandler
    public void priceSettingClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        if (event.getView().getTitle().equals("가격설정")) {
            if (event.getSlot() == 16) {
                player.closeInventory();
                player.sendMessage(title + "채팅에 가격을 입력 해주세요.");
                Bukkit.getPluginManager().registerEvents(new ChatListener(), plugin);
            }
        }
    }
}
