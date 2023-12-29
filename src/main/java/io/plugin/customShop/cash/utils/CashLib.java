package io.plugin.customShop.cash.utils;

import io.plugin.customShop.config.UserConfig;
import io.plugin.customShop.utils.PlayerManager;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class CashLib {

    public static Map<UUID, Integer> userMoney = new HashMap<>();

    /**
     * 캐시를 원하는 만큼 설정합니다. "추가가 아닙니다."
     * @param uuid
     * @param cashSet
     * @return
     */
    public static int cashSet(UUID uuid, Integer cashSet) {
        userMoney.put(uuid, cashSet);
        return cashSet;
    }

    /**
     * 보유하고 있는 캐시를 0값으로 돌려줍니다.
     * @param uuid
     * @return
     */
    public static int cashRemove(UUID uuid) {
        return userMoney.remove(uuid);
    }

    /**
     * 사용자의 캐시를 얻어옵니다.
     * @param uuid
     * @return
     */
    public static int getCash(UUID uuid) {
        Integer userCash = userMoney.get(uuid);
        return userCash != null ? userCash : 0;
    }

    /**
     * 캐쉬를 차감합니다.
     * @param uuid
     * @param cashSubtract
     */
    public static void cashSubtract(UUID uuid, Integer cashSubtract) {
        int getUserCash = getCash(uuid) - cashSubtract;
        userMoney.put(uuid, getUserCash);
    }

    /**
     * 캐쉬를 추가합니다.
     * @param uuid
     * @param cashAdd
     */
    public static void cashAdd(UUID uuid, Integer cashAdd) {
        int getUserCash = getCash(uuid) + cashAdd;
        userMoney.put(uuid, getUserCash);
    }

    /**
     * 유저의 캐시 데이터를 가져옵니다.
     */
    public static void getCashData() {
        for (Player player : PlayerManager.onlinePlayers) {
            YamlConfiguration config = UserConfig.getPlayerConfig(player);
            UUID uuid = player.getUniqueId();
            userMoney.put(uuid, config.getInt("cash"));
        }

        for (OfflinePlayer player : PlayerManager.offlinePlayers) {
            if (!player.isOnline()) {
                YamlConfiguration config = UserConfig.getPlayerConfig(player);
                UUID uuid = player.getUniqueId();
                userMoney.put(uuid, config.getInt("cash"));
            }
        }
    }
}
