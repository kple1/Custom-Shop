package io.plugin.customShop.Cash.listener;

import io.plugin.customShop.Main;
import io.plugin.customShop.utils.Color;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.UUID;

import static io.plugin.customShop.Main.title;
import static io.plugin.customShop.Cash.utils.CashFunction.cashAdd;
import static io.plugin.customShop.Cash.utils.CashFunction.getCash;

public class ServiceGetCash implements Listener {

    @EventHandler
    public void cashSet(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        UUID playerUUID = player.getUniqueId();

        ItemStack itemInHand = player.getInventory().getItemInMainHand();
        ItemMeta itemMeta = itemInHand.getItemMeta();
        if (itemMeta == null) {
            return;
        }

        if (!(event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK)) return;
        if (!itemMeta.hasDisplayName() || !itemMeta.hasLore()) return;

        String key = itemMeta.getDisplayName();
        String extractedNumbers = key.replaceAll("\\D", "");

        if (!extractedNumbers.isEmpty()) {
            int cashValue = Integer.parseInt(extractedNumbers); //추가할 돈 가져오기
            Main.getPlugin().removeItemsFromMainHand(player, 1); //사용한 아이템 삭제

            cashAdd(playerUUID, cashValue); //돈 추가
            player.sendMessage(title + Color.chat("캐쉬가 발급 되었습니다!"));
            player.sendMessage(title + Color.chat("현재 잔액: " + getCash(playerUUID))); // 플레이어의 현재 돈 출력
        }
    }
}
