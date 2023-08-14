package io.plugin.customShop.utils;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class ItemBuild {

    public static ItemStack getLine = new ItemBuilder(Material.OAK_BUTTON)
            .setName("&c&l줄 설정")
            .setLore("  &f> 우클릭시 줄 감소", "  &f> 좌클릭시 줄 증가")
            .build();
}
