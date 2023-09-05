package io.plugin.customShop.command;

import io.plugin.customShop.Main;
import io.plugin.customShop.config.UserConfig;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.util.Collection;
import java.util.UUID;

public class test {

    public static void onCommand(CommandSender sender, String[] args) {
        if (sender instanceof Player) {
            Collection<? extends Player> onlinePlayers = Bukkit.getServer().getOnlinePlayers();
            for (Player player : onlinePlayers) {
                UUID getUUIDPlayer = player.getUniqueId();
                YamlConfiguration config = UserConfig.getPlayerConfig(player);
                config.set("uuid", getUUIDPlayer);
                Main.getPlugin().saveYamlConfiguration();
            }
        }
    }
}
