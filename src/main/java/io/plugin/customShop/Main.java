package io.plugin.customShop;

import io.plugin.customShop.command.MainCommand;
import io.plugin.customShop.listener.InvClickCancel;
import io.plugin.customShop.utils.Color;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;

import static io.plugin.customShop.config.UserConfig.config;
import static io.plugin.customShop.config.UserConfig.playerFile;

public final class Main extends JavaPlugin {

    public static Main plugin;
    public static File uuidFolder;
    public static String title = Color.chat("&f[ &aShop &f] ");

    @Override
    public void onEnable() {
        command();
        listener();
        plugin = this;
    }

    @Override
    public void onDisable() {
        saveDefaultConfig();
    }

    public void command() {
        Bukkit.getPluginCommand("shop").setExecutor(new MainCommand());
    }

    public void listener() {
        Bukkit.getPluginManager().registerEvents(new InvClickCancel(), this);
    }

    public void createPlayerDefaults() {
        YamlConfiguration playerConfig = YamlConfiguration.loadConfiguration(playerFile);
        playerConfig.options().copyDefaults(true);
        saveYamlConfiguration();
    }

    public void saveYamlConfiguration() {
        try {
            config.save(playerFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public File getUuidFolder() {
        return uuidFolder;
    }

    public int getNextAvailableIndex() {
        int nextAvailableIndex = 0;
        while (plugin.getConfig().getString("상점목록." + nextAvailableIndex) != null) {
            nextAvailableIndex++;
        }
        return nextAvailableIndex;
    }

    public static Main getPlugin() {
        return plugin;
    }
}
