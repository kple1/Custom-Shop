package io.plugin.customShop.command;

import io.plugin.customShop.utils.ItemBuild;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class CashIssued implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (sender instanceof Player player) {
            if (!player.isOp()) return true;
            player.getInventory().addItem(ItemBuild.cash(args[1]));
            return true;
        }
        return false;
    }
}
