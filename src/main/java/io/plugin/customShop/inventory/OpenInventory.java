package io.plugin.customShop.inventory;

import io.plugin.customShop.utils.ItemBuild;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import static io.plugin.customShop.Main.plugin;

public class OpenInventory {

    public void openInventoryToCommand(Player player, int size, String name, String[] args) {
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

    public void openInventoryToItemSet(Player player, int size, String name) {
        Inventory inv = Bukkit.createInventory(null, size, name);

        for (int i = 0; i < inv.getSize(); i++) {
            ItemStack item = plugin.getConfig().getItemStack(name + "." + i + ".item");
            if (item == null) {
                continue;
            }
            inv.setItem(i, item);
        }
        player.openInventory(inv);
    }

    public void openInventoryToPriceAndSellSettings(Player player, int size, String name) {
        Inventory inv = Bukkit.createInventory(null, size, name + "상점 구매&판매설정");

        for (int i = 0; i < inv.getSize(); i++) {
            ItemStack item = plugin.getConfig().getItemStack(name + "." + i + ".item");
            if (item == null) {
                continue;
            }
            inv.setItem(i, item);
        }
        player.openInventory(inv);
    }

    public void itemFix(Player player) {
        Inventory inv = Bukkit.createInventory(null, 27, "가격설정");
        inv.setItem(10, ItemBuild.moneyItem());
        inv.setItem(13, ItemBuild.cashItem());
        inv.setItem(16, ItemBuild.priceSet());

        player.openInventory(inv);
    }
}
