package io.plugin.customShop.inventory;

import io.plugin.customShop.utils.ItemBuild;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class EditInventory {

    public void openInventory(Player player, int size, String name) {
        Inventory inv = Bukkit.createInventory(null, size, name);
        inv.setItem(10, ItemBuild.getLine);
        player.openInventory(inv);
        return;
    }
}
