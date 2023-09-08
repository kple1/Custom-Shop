package io.plugin.customShop.config;

import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

public class UserConfig {

    public static YamlConfiguration config = new YamlConfiguration();
    public static File playerFile = new File("plugins/Custom-Shop/playerData/");

    public static YamlConfiguration getPlayerConfig(OfflinePlayer player) {
        playerFile = new File("plugins/Custom-Shop/playerData/" + player.getUniqueId().toString() + ".yml");

        if (!playerFile.exists()) {
            config.options().copyDefaults(true);
        }

        config = YamlConfiguration.loadConfiguration(playerFile);
        return config;
    }
}
