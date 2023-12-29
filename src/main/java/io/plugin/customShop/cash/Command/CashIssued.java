package io.plugin.customShop.cash.Command;

import io.plugin.customShop.utils.ItemBuild;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CashIssued {
    public static void onCommand(CommandSender sender, String[] args) {
        if (sender instanceof Player player) {
            if (!player.isOp()) return;
            player.getInventory().addItem(ItemBuild.cash(args[1]));
        }
    }
}
