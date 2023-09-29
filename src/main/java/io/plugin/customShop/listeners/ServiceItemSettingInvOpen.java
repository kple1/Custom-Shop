package io.plugin.customShop.listeners;

import io.plugin.customShop.inventory.InventoryManager;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;

import static io.plugin.customShop.Main.plugin;
import static io.plugin.customShop.config.ConfigSection.configSection;

public class ServiceItemSettingInvOpen implements Listener {

    public static Map<String, Integer> saveSlot = new HashMap<>();
    public static Map<String, String> inventory = new HashMap<>();
    public static Map<String, ItemStack> getLore = new HashMap<>();

    @EventHandler
    public void itemPriceClickToOpenSetting(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();

        for (String list : configSection("상점목록").getKeys(false)) {

            String getShopName = plugin.getConfig().getString("상점목록." + list);
            if (getShopName == null) continue;
            if (event.getView().getTitle().equals(getShopName + "상점 구매&판매설정")) {
                inventory.put("inventoryName", getShopName);

                if (event.getCurrentItem() == null) return;
                if (event.getCurrentItem().getType() == Material.AIR) return;
                if (event.getClickedInventory() == null) return;
                if (event.getClickedInventory().equals(player.getInventory())) return;
                if (event.getRawSlot() >= event.getInventory().getSize()) return;

                for (int i = 0; i < event.getInventory().getSize(); i++) {
                    if (event.getSlot() == i) {
                        saveSlot.put("saveSlot", event.getSlot());
                        getLore.put("lore", event.getClickedInventory().getItem(event.getSlot()));
                        InventoryManager.itemFix(player);
                        return;
                    }
                }
            }
        }
    }
}
