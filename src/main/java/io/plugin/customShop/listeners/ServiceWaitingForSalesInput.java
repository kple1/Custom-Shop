package io.plugin.customShop.listeners;

import io.plugin.customShop.Main;
import io.plugin.customShop.utils.ItemBuild;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.util.Objects;

import static io.plugin.customShop.Main.title;

public class ServiceWaitingForSalesInput implements Listener {
    @EventHandler
    public void priceSettingClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        if (!event.getView().getTitle().equals("가격설정")) return;
        if (Objects.equals(event.getCurrentItem(), ItemBuild.priceSet())) {
            player.closeInventory();
            player.sendMessage(title + "채팅에 가격을 입력 해주세요.");
            Bukkit.getPluginManager().registerEvents(new ServicePriceInputChat(), Main.getPlugin());
        }
    }
}
