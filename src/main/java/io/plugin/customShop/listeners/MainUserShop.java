package io.plugin.customShop.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class MainUserShop implements Listener {

    @EventHandler
    public void mainUserShopItemClick(InventoryClickEvent event) {
        //아이템의 Lore를 가져오는 방식으로 운영
        //Lore에 Cash, Money로 구별
        //Lore에 돈 구별
    }
}
