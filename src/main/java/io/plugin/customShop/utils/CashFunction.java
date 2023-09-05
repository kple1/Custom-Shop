package io.plugin.customShop.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class CashFunction {

    public static final Map<UUID, Integer> userMoney = new HashMap<>();


    public static int cashSet(UUID uuid, Integer cashSet) {
        userMoney.put(uuid, cashSet);
        return cashSet;
    }

    public static int cashRemove(UUID uuid) {
        userMoney.put(uuid, 0);
        return 0;
    }

    public static int getCash(UUID uuid) {
        Integer userCash = userMoney.get(uuid);
        return userCash != null ? userCash : 0;
    }

    public static void cashSubtract(UUID uuid, Integer cashSubtract) {
        int getUserCash = getCash(uuid) - cashSubtract;
        userMoney.put(uuid, getUserCash);
    }

    public static void cashAdd(UUID uuid, Integer cashAdd) {
        int getUserCash = getCash(uuid) + cashAdd;
        userMoney.put(uuid, getUserCash);
    }
}
