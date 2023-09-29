package io.plugin.customShop.listeners;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

import java.util.*;

public class ShopMainCenter {

    public static Map<String, String> changeEcoSetting = new HashMap<>();
    public static Map<String, String> changeSellOrBuySetting = new HashMap<>();

    public static void registerListeners(Plugin plugin) {
        Bukkit.getPluginManager().registerEvents(new ServiceItemPriceSetting(), plugin);
        Bukkit.getPluginManager().registerEvents(new ServiceItemSettingInvOpen(), plugin);
        Bukkit.getPluginManager().registerEvents(new ServicePriceInputChat(), plugin);
        Bukkit.getPluginManager().registerEvents(new ServiceWaitingForSalesInput(), plugin);
        Bukkit.getPluginManager().registerEvents(new ServiceRegistrationItem(), plugin);
        Bukkit.getPluginManager().registerEvents(new ServiceSaveShopSettingsItem(), plugin);
        Bukkit.getPluginManager().registerEvents(new ServiceShopLineEdit(), plugin);
        Bukkit.getPluginManager().registerEvents(new ServiceChangeEconomy(), plugin);
        Bukkit.getPluginManager().registerEvents(new ServiceChangeSellOrBuySetting(), plugin);
    }
}