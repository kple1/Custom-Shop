package io.plugin.customShop.listener;

import io.plugin.customShop.Main;
import io.plugin.customShop.config.UserConfig;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class CashSet implements Listener {

    @EventHandler
    public void cashSet(PlayerInteractEvent event) {

        Player player = event.getPlayer();
        YamlConfiguration config = UserConfig.getPlayerConfig(player);
        ItemStack itemInHand = player.getInventory().getItemInMainHand();
        ItemMeta itemMeta = itemInHand.getItemMeta();

        if (!(event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK)) return;
        if (!itemMeta.hasDisplayName() || !itemMeta.hasLore()) return;

        String key = itemMeta.getDisplayName();
        String extractedNumbers = key.replaceAll("\\D", "");

        if (!extractedNumbers.isEmpty()) {
            int cashValue = Integer.parseInt(extractedNumbers);
            Main.getPlugin().removeItemsFromMainHand(player, 1);

            int getCash = config.getInt("cash");
            config.set("cash", getCash + cashValue);
            Main.getPlugin().saveYamlConfiguration();
        }
    }
}
