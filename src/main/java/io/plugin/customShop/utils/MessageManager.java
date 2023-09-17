package io.plugin.customShop.utils;

import org.bukkit.entity.Player;

public class MessageManager {

    public static void args0(Player player) {
        player.sendMessage("");
        player.sendMessage(Color.chat("[ &bCustom&f-&bShop &c&l&n명령어 사용법 &f]"));
        player.sendMessage("————————————————");
        player.sendMessage("/상점 생성 <이름>");
        player.sendMessage("/상점 열기 <이름>");
        player.sendMessage("/상점 편집 <이름>");
        player.sendMessage("/상점 목록 <이름>");
        player.sendMessage("/상점 삭제 <이름>");
        player.sendMessage("————————————————");
        player.sendMessage("");
    }
}
