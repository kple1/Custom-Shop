package io.plugin.customShop.listener;

import io.plugin.customShop.Main;
import io.plugin.customShop.utils.Color;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Map;
import java.util.UUID;

import static io.plugin.customShop.Main.title;
import static io.plugin.customShop.Main.userMoney;

public class CashSet implements Listener {

    @EventHandler
    public void cashSet(PlayerInteractEvent event) {

        int getMoney = 0;
        for (Map.Entry<UUID, Integer> pair : userMoney.entrySet()) {
            getMoney = pair.getValue();
        }

        Player player = event.getPlayer();
        UUID getUUID = player.getUniqueId();
        //YamlConfiguration config = UserConfig.getPlayerConfig(player);
        ItemStack itemInHand = player.getInventory().getItemInMainHand();
        ItemMeta itemMeta = itemInHand.getItemMeta();

        if (!(event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK)) return;
        if (!itemMeta.hasDisplayName() || !itemMeta.hasLore()) return;

        String key = itemMeta.getDisplayName();
        String extractedNumbers = key.replaceAll("\\D", "");

        if (!extractedNumbers.isEmpty()) {
            int cashValue = Integer.parseInt(extractedNumbers);
            Main.getPlugin().removeItemsFromMainHand(player, 1);

            userMoney.put(getUUID, getMoney + cashValue);
            player.sendMessage(title + Color.chat("캐쉬가 발급 되었습니다!"));
            player.sendMessage(title + Color.chat("현재 잔액: " + getMoney));
            //int getCash = config.getInt("cash");
            //config.set("cash", getCash + cashValue);
            //Main.getPlugin().saveYamlConfiguration();
        }
    }
}
