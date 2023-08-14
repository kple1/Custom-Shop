package io.plugin.customShop.inventory;

import io.plugin.customShop.utils.ItemBuild;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class EditInventory {

    public void editInventory(Player player, int size, String name, String[] args) {
        Inventory inv = Bukkit.createInventory(null, size, name);
        inv.setItem(10, ItemBuild.getLine(args));
        inv.setItem(13, ItemBuild.itemSet());
        inv.setItem(16, ItemBuild.itemEdit());
        player.openInventory(inv);
        return;
    }
}
