package io.plugin.customShop.command;

import io.plugin.customShop.inventory.EditInventory;
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

public class EditShop implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (sender instanceof Player player) {
            if (!player.isOp()) return true;

            if (args.length < 2 || args[1].isEmpty()) {
                player.sendMessage(title + Color.chat("상점을 편집할 &c&l이름&f을 입력 해주세요!"));
                return true;
            }

            boolean found = false;
            ConfigurationSection configSection = plugin.getConfig().getConfigurationSection("상점목록");
            for (String list : configSection.getKeys(false)) {
                String getShopName = plugin.getConfig().getString("상점목록." + list);
                if (Objects.equals(getShopName, args[1])) {
                    EditInventory editInventory = new EditInventory();

                    editInventory.openInventory(player, 27, args[1] + " 편집메뉴");
                    player.sendMessage(title + Color.chat("&c&l" + args[1] + "&f편집 창이 오픈되었습니다!"));
                    found = true;
                    break;
                }
            }

            if (!found) {
                player.sendMessage(title + Color.chat("&c&l" + args[1] + "&f이란 상점은 존재하지 않습니다!"));
                return true;
            }
        }
        return false;
    }
}
