package io.plugin.customShop.inventory;


import io.plugin.customShop.utils.ItemBuild;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import static io.plugin.customShop.Main.plugin;

public class InventoryManager {

    public static void openInventoryToCommand(Player player, int size, String name, String[] args) {
        Inventory inv = Bukkit.createInventory(null, size, name);

        for (int i = 0; i < inv.getSize(); i++) {
            ItemStack item = plugin.getConfig().getItemStack(args[1] + "." + i + ".item");
            if (item == null) {
                continue;
            }
            inv.setItem(i, item);
        }
        player.openInventory(inv);
    }

    public static void openInventory(Player player, int size, String name, String addName) {
        Inventory inv = Bukkit.createInventory(null, size, name + addName);

        for (int i = 0; i < inv.getSize(); i++) {
            ItemStack item = plugin.getConfig().getItemStack(name + "." + i + ".item");
            if (item == null) {
                continue;
            }
            inv.setItem(i, item);
        }
        player.openInventory(inv);
    }

    public static void itemFix(Player player) {
        Inventory inv = Bukkit.createInventory(null, 27, "가격설정");
        inv.setItem(10, ItemBuild.ecoItem());
        inv.setItem(16, ItemBuild.priceSet());
        player.openInventory(inv);
    }

    public static void editInventory(Player player, int size, String name, String[] args) {
        Inventory inv = Bukkit.createInventory(null, size, name);
        inv.setItem(10, ItemBuild.getLine(args));
        inv.setItem(13, ItemBuild.itemSet());
        inv.setItem(16, ItemBuild.itemEdit());
        player.openInventory(inv);
    }
}
