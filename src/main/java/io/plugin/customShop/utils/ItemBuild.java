package io.plugin.customShop.utils;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import static io.plugin.customShop.Main.plugin;

public class ItemBuild {

    public static ItemStack getLine(String[] args) {
        int line = plugin.getConfig().getInt(args[1] + ".size");
        int displayValue = (line - 1) / 9 + 1;

        return new ItemBuilder(Material.OAK_BUTTON)
                .setName("&c&l줄 설정")
                .setLore("  &a현재 줄&f: " + displayValue + "줄", "  &f> 우클릭시 줄 &n감소&f", "  &f> 좌클릭시 줄 &n증가&f")
                .build();
    }

    public static ItemStack itemSet() {
        return new ItemBuilder(Material.PRISMARINE_SHARD)
                .setName("&c&l아이템 설정")
                .setLore("  &f> 아이템을 넣어 아이템을 설정합니다.")
                .build();
    }

    public static ItemStack itemEdit() {
        return new ItemBuilder(Material.NETHER_STAR)
                .setName("&c&l아이템 편집")
                .setLore("  &f> 아이템에대한 &b판매 &for &c구매 &a&n가격&f을 설정합니다.")
                .build();
    }

    public static ItemStack cash(String cash) {
        return new ItemBuilder(Material.PAPER)
                .setName("&f[ &a" + cash + " &eCash &f]")
                .setLore("  &f> 우클릭시 &b" + cash + "Cash&f를 얻습니다!")
                .build();
    }
}