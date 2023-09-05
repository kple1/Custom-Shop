package io.plugin.customShop.command;

import io.plugin.customShop.Main;
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
                player.sendMessage("[ Custom-Shop 명령어 사용법 ]");
                player.sendMessage("/상점 생성 <이름>");
                player.sendMessage("/상점 열기 <이름>");
                player.sendMessage("/상점 편집 <이름>");
                player.sendMessage("/상점 목록 <이름>");
                player.sendMessage("/상점 삭제 <이름>");
                return true;
            }

            switch (args[0]) {
                case "생성" -> StoreManagement.ShopCreate.onCommand(sender, args);
                case "열기" -> StoreManagement.OpenShop.onCommand(sender, args);
                case "편집" -> StoreManagement.EditShop.onCommand(sender, args);
                case "목록" -> StoreManagement.ShopList.onCommand(sender, args);
                case "삭제" -> StoreManagement.DeleteShop.onCommand(sender, args);
                case "캐쉬발급" -> CashIssued.onCommand(sender, args);
                case "dataSave" -> {
                    Main.getPlugin().allPlayerSaveData();
                    player.sendMessage(title + "데이터가 저장되었습니다.");
                }
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

        if (args.length == 1) {
            tabList.add("생성");
            tabList.add("열기");
            tabList.add("편집");
            tabList.add("목록");
            tabList.add("삭제");
            tabList.add("캐쉬발급");
            tabList.add("dataSave");
            tabList.add("testSave");
            return StringUtil.copyPartialMatches(args[0], tabList, new ArrayList<>());
        }
        return tabList;
    }
}
