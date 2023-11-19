package io.plugin.customShop.Cash.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public interface CashLib {

    Map<UUID, Integer> userMoney = new HashMap<>();

    /**
     * 캐시를 원하는 만큼 설정합니다. "추가가 아닙니다."
     * @param uuid
     * @param cashSet
     * @return
     */
    static int cashSet(UUID uuid, Integer cashSet) {
        userMoney.put(uuid, cashSet);
        return cashSet;
    }

    /**
     * 보유하고 있는 캐시를 0값으로 돌려줍니다.
     * @param uuid
     * @return
     */
    static int cashRemove(UUID uuid) {
        return userMoney.remove(uuid);
    }

    /**
     * 사용자의 캐시를 얻어옵니다.
     * @param uuid
     * @return
     */
    static int getCash(UUID uuid) {
        Integer userCash = userMoney.get(uuid);
        return userCash != null ? userCash : 0;
    }

    /**
     * 캐쉬를 차감합니다.
     * @param uuid
     * @param cashSubtract
     */
    static void cashSubtract(UUID uuid, Integer cashSubtract) {
        int getUserCash = getCash(uuid) - cashSubtract;
        userMoney.put(uuid, getUserCash);
    }

    /**
     * 캐쉬를 추가합니다.
     * @param uuid
     * @param cashAdd
     */
    static void cashAdd(UUID uuid, Integer cashAdd) {
        int getUserCash = getCash(uuid) + cashAdd;
        userMoney.put(uuid, getUserCash);
    }
}
