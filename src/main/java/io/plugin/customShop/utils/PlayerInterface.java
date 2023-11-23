package io.plugin.customShop.utils;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.util.Collection;

public interface PlayerInterface {
    Collection<? extends Player> onlinePlayers = Bukkit.getServer().getOnlinePlayers();
    OfflinePlayer[] offlinePlayers = Bukkit.getServer().getOfflinePlayers();
}
