package org.leng.listeners;

import org.leng.LengWhiteList;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinListener implements Listener {
    private final LengWhiteList plugin;

    public PlayerJoinListener(LengWhiteList plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        String playerName = event.getPlayer().getName();
        if (!plugin.getConfig().getBoolean("whitelist." + playerName, false)) {
            event.getPlayer().kickPlayer("You are not on the whitelist.");
        }
    }
}