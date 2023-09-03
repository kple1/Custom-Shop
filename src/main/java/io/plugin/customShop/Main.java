package io.plugin.customShop;

import io.plugin.customShop.bStats.Metrics;
import io.plugin.customShop.command.CommandCenter;
import io.plugin.customShop.listener.*;
import io.plugin.customShop.utils.Color;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
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
        this.saveConfig();
        command();
        listener();
        plugin = this;
        new Metrics(this, 19558);
    }

    @Override
    public void onDisable() {
        saveDefaultConfig();
    }

    public void command() {
        Bukkit.getPluginCommand("shop").setExecutor(new CommandCenter());
    }

    public void listener() {
        Bukkit.getPluginManager().registerEvents(new InvClickCancel(), this);
        Bukkit.getPluginManager().registerEvents(new LineEdit(), this);
        Bukkit.getPluginManager().registerEvents(new ItemSet(), this);
        Bukkit.getPluginManager().registerEvents(new ItemSaveForItemSet(), this);
        Bukkit.getPluginManager().registerEvents(new ItemPriceBuySetting(), this);
        Bukkit.getPluginManager().registerEvents(new CashSet(), this);
        Bukkit.getPluginManager().registerEvents(new ItemSettingOpen(), this);
        Bukkit.getPluginManager().registerEvents(new ChatListener(), this);
        Bukkit.getPluginManager().registerEvents(new PriceSettingClick(), this);
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

    public void removeItemsFromMainHand(Player player, int amountToRemove) {
        int getAmount = player.getInventory().getItemInMainHand().getAmount();
        int itemAmountSet = getAmount - amountToRemove;
        player.getInventory().getItemInMainHand().setAmount(itemAmountSet);
    }

    public static Main getPlugin() {
        return plugin;
    }
}
