package org.leng.listeners;

import org.leng.LengWhiteList;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.entity.Player;
import org.bukkit.ChatColor;

public class PlayerJoinListener implements Listener {
    private final LengWhiteList plugin;

    public PlayerJoinListener(LengWhiteList plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        String playerName = player.getName();

        // 检查是否为假人
        if (isFakePlayer(playerName)) {
            // 如果是假人，允许加入，不检查白名单
            return;
        }

        // 检查白名单
        if (!plugin.getConfig().getBoolean("whitelist." + playerName, false)) {
            player.kickPlayer(ChatColor.RED + "你不在白名单中。");
        }
    }

    private boolean isFakePlayer(String playerName) {
        // 假人名字通常以 "[Fake]" 开头
        return playerName.startsWith("[Fake]");
    }
}