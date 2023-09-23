package io.plugin.customShop.config;

import org.bukkit.configuration.ConfigurationSection;

import static io.plugin.customShop.Main.plugin;

public class ConfigSection {
    public static ConfigurationSection configSection(String getSection) {
        return  plugin.getConfig().getConfigurationSection(getSection);
    }
}
