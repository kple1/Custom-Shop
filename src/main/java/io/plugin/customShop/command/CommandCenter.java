package io.plugin.customShop.command;

import io.plugin.customShop.Cash.Command.CashIssued;
import io.plugin.customShop.Main;
import io.plugin.customShop.utils.MessageManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static io.plugin.customShop.Main.title;

public class CommandCenter implements CommandExecutor, TabExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (sender instanceof Player player) {
            if (args.length == 0) {
                MessageManager.sendAnnouncementArgs0(player);
                return true;
            }

            switch (args[0]) {
                case "생성" -> StoreManagement.ServiceCommandShopCreate.onCommand(sender, args);
                case "열기" -> StoreManagement.ServiceCommandOpenShop.onCommand(sender, args);
                case "편집" -> StoreManagement.ServiceCommandEditShop.onCommand(sender, args);
                case "목록" -> StoreManagement.ServiceCommandShopList.onCommand(sender, args);
                case "삭제" -> StoreManagement.DeleteShop.onCommand(sender, args);
                case "캐쉬발급" -> CashIssued.onCommand(sender, args);
                case "dataSave" -> Main.getPlugin().allPlayerSaveData(player);
                default -> player.sendMessage(title + "올바르지 않은 명령어 입니다.");
            }
        }
        return false;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if (!(sender instanceof Player)) {
            return Collections.emptyList();
        }

        final List<String> tabList = new ArrayList<>();
        String[] tabStrings = {"생성", "열기", "편집", "목록", "삭제", "캐쉬발급", "dataSave"};

        if (args.length == 1) {
            for (int i = 0; i < 7; i++) {
                tabList.add(i, tabStrings[i]);
            }
            return StringUtil.copyPartialMatches(args[0], tabList, new ArrayList<>());
        }
        return tabList;
    }
}
