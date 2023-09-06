package io.plugin.customShop.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class CashFunction {

    public static final Map<UUID, Integer> userMoney = new HashMap<>();

    //캐시를 원하는 만큼 설정합니다. "추가가 아닙니다."
    public static int cashSet(UUID uuid, Integer cashSet) {
        userMoney.put(uuid, cashSet);
        return cashSet;
    }

    //보유하고 있는 캐시롤 0값으로 돌려줍니다.
    public static int cashRemove(UUID uuid) {
        return userMoney.remove(uuid);
    }

    //사용자의 캐시를 얻어옵니다.
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
