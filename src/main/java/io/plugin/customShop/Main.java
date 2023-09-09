package io.plugin.customShop;

import io.plugin.customShop.Cash.listener.ServiceGetCash;
import io.plugin.customShop.config.UserConfig;
import io.plugin.customShop.bStats.Metrics;
import io.plugin.customShop.command.CommandCenter;
import io.plugin.customShop.listener.*;
import io.plugin.customShop.utils.Color;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;
import java.util.Collection;
import java.util.UUID;

import static io.plugin.customShop.config.UserConfig.config;
import static io.plugin.customShop.config.UserConfig.playerFile;
import static io.plugin.customShop.Cash.utils.CashFunction.getCash;
import static io.plugin.customShop.Cash.utils.CashFunction.userMoney;

public final class Main extends JavaPlugin {

    public static Main plugin;
    public static String title = Color.chat("&f[ &aShop &f] ");

    private void getCashData() {
        Collection<? extends Player> onlinePlayers = Bukkit.getServer().getOnlinePlayers();
        for (Player player : onlinePlayers) {
            YamlConfiguration config = UserConfig.getPlayerConfig(player);
            UUID uuid = player.getUniqueId();
            userMoney.put(uuid, config.getInt("cash"));
        }

        OfflinePlayer[] offlinePlayers = Bukkit.getServer().getOfflinePlayers();
        for (OfflinePlayer player : offlinePlayers) {
            if (!player.isOnline()) {
                YamlConfiguration config = UserConfig.getPlayerConfig(player);
                UUID uuid = player.getUniqueId();
                userMoney.put(uuid, config.getInt("cash"));
            }
        }
    }

    public void allPlayerSaveData(Player player) {
        int getOnlinePlayerAmount = 0;
        Collection<? extends Player> onlinePlayers = Bukkit.getServer().getOnlinePlayers();
        for (Player loopAllPlayer : onlinePlayers) {
            UUID playerUUID = loopAllPlayer.getUniqueId();
            YamlConfiguration config = UserConfig.getPlayerConfig(loopAllPlayer);
            config.set("cash", getCash(playerUUID));
            this.saveYamlConfiguration();

            getOnlinePlayerAmount = onlinePlayers.size();
            if (player != null) {
                player.sendMessage(title + getOnlinePlayerAmount + "명의 온라인 유저의 데이터를 저장하였습니다.");
            }
        }

        OfflinePlayer[] offlinePlayers = Bukkit.getServer().getOfflinePlayers();
        for (OfflinePlayer loopAllPlayer : offlinePlayers) {
            if (!loopAllPlayer.isOnline()) {
                YamlConfiguration config = UserConfig.getPlayerConfig(loopAllPlayer);
                UUID getPayerUUID = loopAllPlayer.getUniqueId();
                config.set("cash", getCash(getPayerUUID));
                this.saveYamlConfiguration();

                int getOfflinePlayerAmount = offlinePlayers.length - getOnlinePlayerAmount;
                if (player != null) {
                    player.sendMessage(title + getOfflinePlayerAmount + "명의 오프라인 유저의 데이터를 저장하였습니다.");
                }
            }
        }
    }

    @Override
    public void onEnable() {
        //function
        this.saveConfig();
        this.command();
        this.listener();

        //get User Cash Data
        getCashData();

        //instance
        new Metrics(this, 19558);
        plugin = this;
    }

    @Override
    public void onDisable() {
        this.allPlayerSaveData(null);
        saveDefaultConfig();
    }

    public void command() {
        Bukkit.getPluginCommand("shop").setExecutor(new CommandCenter());
    }

    public void listener() {
        Bukkit.getPluginManager().registerEvents(new ShopItemClickCancel(), this);
        Bukkit.getPluginManager().registerEvents(new ServiceGetCash(), this);
        ShopMainCenter.registerListeners(this);
    }

    public void saveYamlConfiguration() {
        try {
            config.save(playerFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
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
