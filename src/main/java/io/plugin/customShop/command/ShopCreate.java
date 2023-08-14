package io.plugin.customShop.command;

import io.plugin.customShop.Main;
import io.plugin.customShop.utils.Color;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

import static io.plugin.customShop.Main.plugin;
import static io.plugin.customShop.Main.title;

public class ShopCreate implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (sender instanceof Player player) {
            if (!player.isOp()) return true;

            if (args.length < 2 || args[1].isEmpty()) {
                player.sendMessage(title + Color.chat("상점을 만들 &c&l이름&f을 입력 해주세요!"));
                return true;
            }

            ConfigurationSection configSection = plugin.getConfig().getConfigurationSection("상점목록");
            for (String list : configSection.getKeys(false)) {
                String getShopName = plugin.getConfig().getString("상점목록." + list);
                if (Objects.equals(getShopName, args[1])) {
                    player.sendMessage(title + Color.chat("이미&c&l " + args[1] + "&f이란 상점은 존재합니다!"));
                    return true;
                }
            }

            int nextAvailableIndex = Main.getPlugin().getNextAvailableIndex();
            plugin.getConfig().set("상점목록." + nextAvailableIndex, args[1]);
            plugin.getConfig().set(args[1] + ".size", 27); //상점 생성시 기본값으로 설정되는 크기
            plugin.saveConfig();
            player.sendMessage(title + Color.chat("&c&l" + args[1] + "&f상점이 생성되었습니다!"));
            return true;
        }
        return false;
    }
}
