package io.plugin.customShop.command;

import io.plugin.customShop.Main;
import io.plugin.customShop.inventory.InventoryManager;
import io.plugin.customShop.utils.Color;
import io.plugin.customShop.utils.ItemBuild;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

import java.util.Objects;

import static io.plugin.customShop.Main.plugin;
import static io.plugin.customShop.Main.title;

public class StoreManagement {

    public static class DeleteShop {
        public static void onCommand(CommandSender sender, String[] args) {
            if (sender instanceof Player player) {
                if (!player.isOp()) return;

                if (args.length < 2 || args[1].isEmpty()) {
                    player.sendMessage(title + Color.chat("상점을 삭제할 &c&l이름&f을 입력 해주세요!"));
                    return;
                }

                boolean found = false;
                ConfigurationSection configSection = plugin.getConfig().getConfigurationSection("상점목록");
                if (configSection == null) {
                    return;
                }

                for (String list : configSection.getKeys(false)) {
                    String getShopName = plugin.getConfig().getString("상점목록." + list);
                    if (Objects.equals(getShopName, args[1])) {
                        plugin.getConfig().set("상점목록." + list, null);
                        plugin.getConfig().set(args[1], null);
                        plugin.saveConfig();
                        player.sendMessage(title + "상점이 성공적으로 삭제되었습니다!");
                        return;
                    }
                }

                if (!found) {
                    player.sendMessage(title + "해당 상점은 존재하지 않습니다.");
                }
            }
        }
    }

    public static class ServiceCommandEditShop {
        public static void onCommand(CommandSender sender, String[] args) {
            if (sender instanceof Player player) {
                if (!player.isOp()) return;

                if (args.length < 2 || args[1].isEmpty()) {
                    player.sendMessage(title + Color.chat("상점을 편집할 &c&l이름&f을 입력 해주세요!"));
                    return;
                }

                boolean found = false;
                ConfigurationSection configSection = plugin.getConfig().getConfigurationSection("상점목록");
                for (String list : configSection.getKeys(false)) {
                    String getShopName = plugin.getConfig().getString("상점목록." + list);
                    if (Objects.equals(getShopName, args[1])) {
                        InventoryManager.editInventory(player, 27, args[1] + Color.chat("상점 &c편집&8메뉴"), args);
                        player.sendMessage(title + Color.chat("&c&l" + args[1] + "&f편집 창이 오픈되었습니다!"));
                        found = true;
                        break;
                    }
                }

                if (!found) {
                    player.sendMessage(title + Color.chat("&c&l" + args[1] + "&f이란 상점은 존재하지 않습니다!"));
                }
            }
        }
    }

    public static class ServiceCommandOpenShop {
        public static void onCommand(CommandSender sender, String[] args) {
            if (sender instanceof Player player) {
                if (!player.isOp()) return;

                if (args.length < 2 || args[1].isEmpty()) {
                    player.sendMessage(title + Color.chat("상점을 오픈할 &c&l이름&f을 입력 해주세요!"));
                    return;
                }

                boolean found = false;
                ConfigurationSection configSection = plugin.getConfig().getConfigurationSection("상점목록");
                for (String list : configSection.getKeys(false)) {
                    String getShopName = plugin.getConfig().getString("상점목록." + list);

                    if (Objects.equals(getShopName, args[1])) {
                        int size = plugin.getConfig().getInt(args[1] + ".size");

                        InventoryManager.openInventoryToCommand(player, size, args[1] + "상점", args);
                        player.sendMessage(title + Color.chat("&c&l" + args[1] + "&f상점이 오픈되었습니다!"));
                        found = true;
                        break;
                    }
                }

                if (!found) {
                    player.sendMessage(title + Color.chat("&c&l" + args[1] + "&f이란 상점은 존재하지 않습니다!"));
                }
            }
        }
    }

    public static class ServiceCommandShopCreate {
        public static void onCommand(CommandSender sender, String[] args) {
            if (sender instanceof Player player) {
                if (!player.isOp()) return;

                if (args.length < 2 || args[1].isEmpty()) {
                    player.sendMessage(title + Color.chat("상점을 만들 &c&l이름&f을 입력 해주세요!"));
                    return;
                }

                ConfigurationSection configSection = plugin.getConfig().getConfigurationSection("상점목록");
                if (configSection == null) {
                    serviceNormalShopSetting(player, args);
                    return;
                }

                for (String list : configSection.getKeys(false)) {
                    String getShopName = plugin.getConfig().getString("상점목록." + list);
                    if (Objects.equals(getShopName, args[1])) {
                        player.sendMessage(title + Color.chat("이미&c&l " + args[1] + "&f이란 상점은 존재합니다!"));
                        return;
                    }
                }
                serviceNormalShopSetting(player, args);
            }
        }

        public static void serviceNormalShopSetting(Player player, String[] args) {
            int nextAvailableIndex = Main.getPlugin().getNextAvailableIndex();
            plugin.getConfig().set("상점목록." + nextAvailableIndex, args[1]);
            plugin.getConfig().set(args[1] + ".size", 27); //상점 생성시 기본값으로 설정되는 크기
            plugin.saveConfig();
            player.sendMessage(title + Color.chat("&c&l" + args[1] + "&f상점이 생성되었습니다!"));
        }
    }

    public static class ServiceCommandShopList {
        public static void onCommand(CommandSender sender, String[] args) {
            if (sender instanceof Player player) {
                if (!player.isOp()) return;
                if (args.length > 2) return;

                ConfigurationSection configSection = plugin.getConfig().getConfigurationSection("상점목록");
                if (configSection == null) return;

                player.sendMessage("");
                player.sendMessage("〔 상점 목록 〕");
                for (String key : configSection.getKeys(false)) {
                    String getSection = plugin.getConfig().getString("상점목록." + key);
                    player.sendMessage(Color.chat("  &b● &f" + getSection));
                }
                player.sendMessage("");
            }
        }
    }
}
