package io.plugin.customShop.listeners;

import io.plugin.customShop.inventory.InventoryManager;
import io.plugin.customShop.utils.ItemBuild;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChatEvent;

import static io.plugin.customShop.Main.plugin;
import static io.plugin.customShop.Main.title;
import static io.plugin.customShop.config.ConfigSection.configSection;
import static io.plugin.customShop.listeners.ShopMainCenter.changeSellOrBuySetting;

public class ServicePriceInputChat implements Listener {

    public ServicePriceInputChat() {}

    //채팅으로 가격입력
    @EventHandler
    public void onChat(PlayerChatEvent event) {
        Player getMessagePlayer = event.getPlayer();
        String message = event.getMessage();

        if (!message.isEmpty() || !isNumeric(message)) {

            boolean found = false;
            for (String list : configSection("상점목록").getKeys(false)) {
                String getShopName = plugin.getConfig().getString("상점목록." + list);
                plugin.getConfig().set(getShopName + "." + ServiceItemSettingInvOpen.saveSlot.get("saveSlot") + "." + changeSellOrBuySetting.get("changePriceSetting"), message);
                plugin.getConfig().set(getShopName + "." + ServiceItemSettingInvOpen.saveSlot.get("saveSlot") + ".item", ItemBuild.serviceItemSettingInvOpen_getLore_add(message));
                plugin.saveConfig();

                found = true;
                break;
            }

            if (found) {
                InventoryManager.itemFix(getMessagePlayer);
                getMessagePlayer.sendMessage(title + "가격설정이 완료되었습니다!");
            }
            HandlerList.unregisterAll(this);
            event.setCancelled(true);
        }
    }

    private boolean isNumeric(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
