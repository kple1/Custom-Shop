package io.plugin.customShop.listeners;

import io.plugin.customShop.utils.Color;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import static io.plugin.customShop.Main.title;
import static io.plugin.customShop.utils.ItemBuild.sellOrBuySetting;

public class ServiceChangeSellOrBuySetting implements Listener {

    @EventHandler
    public void changePriceSetting(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        if (!event.getView().getTitle().equals("가격설정")) return;
        if (event.getSlot() == 13) {
            ShopMainCenter.changeSellOrBuySetting.putIfAbsent("changePriceSetting", "buy");
            if (ShopMainCenter.changeSellOrBuySetting.get("changePriceSetting").equals("buy")) {
                ShopMainCenter.changeSellOrBuySetting.put("changePriceSetting", "sell");
                player.sendMessage(title + Color.chat("&bsell&f로 변경되었습니다."));
            } else if (ShopMainCenter.changeSellOrBuySetting.get("changePriceSetting").equals("sell")) {
                ShopMainCenter.changeSellOrBuySetting.put("changePriceSetting", "buy");
                player.sendMessage(title + Color.chat("&abuy&f로 변경되었습니다."));
            }
            // 클릭한 후 아이템 이름과 설명을 업데이트
            ItemStack updatedItem = sellOrBuySetting();
            player.getOpenInventory().setItem(13, updatedItem);
        }
    }
}
