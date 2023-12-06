package io.plugin.customShop.utils;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.util.Collection;

public class PlayerManager {
    public static final Collection<? extends Player> onlinePlayers = Bukkit.getServer().getOnlinePlayers();
    public static final OfflinePlayer[] offlinePlayers = Bukkit.getServer().getOfflinePlayers();
}
