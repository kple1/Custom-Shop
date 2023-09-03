package io.plugin.customShop.listener;

import io.plugin.customShop.config.UserConfig;
import io.plugin.customShop.inventory.InventoryManager;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.inventory.Inventory;

import static io.plugin.customShop.Main.plugin;
import static io.plugin.customShop.Main.title;

public class ChatListener implements Listener {

    public ChatListener() {}

    @EventHandler
    public void onChat(PlayerChatEvent event) {
        Player getMessagePlayer = event.getPlayer();
        String message = event.getMessage();
        YamlConfiguration config = UserConfig.getPlayerConfig(getMessagePlayer);

        if (!message.isEmpty()) {
            if (!isNumeric(message)) {
                return;
            }
            ConfigurationSection configSection = plugin.getConfig().getConfigurationSection("상점목록");
            for (String list : configSection.getKeys(false)) {
                int slot = config.getInt("saveSlot");
                String getShopName = plugin.getConfig().getString("상점목록." + list);

                plugin.getConfig().set(getShopName + "." + slot + ".buy", message);
                plugin.saveConfig();
                getMessagePlayer.sendMessage(title + "가격설정이 완료되었습니다!");

                InventoryManager.itemFix(getMessagePlayer);
                event.setCancelled(true);
                break;
            }
        }
        HandlerList.unregisterAll(this);
        event.setCancelled(true);
    }

    private boolean isNumeric(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
