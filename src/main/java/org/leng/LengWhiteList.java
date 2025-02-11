package org.leng;

import org.bukkit.plugin.java.JavaPlugin;
import org.leng.commands.CommandHandler; // 确保正确导入
import org.leng.listeners.PlayerJoinListener; // 确保正确导入

public class LengWhiteList extends JavaPlugin {
    @Override
    public void onEnable() {
        saveDefaultConfig(); // 保存默认配置文件
        getCommand("lwl").setExecutor(new CommandHandler(this)); // 使用正确的类名
        getServer().getPluginManager().registerEvents(new PlayerJoinListener(this), this); // 使用正确的类名
        getLogger().info("LengWhiteList enabled!");
    }

    @Override
    public void onDisable() {
        getLogger().info("LengWhiteList disabled!");
    }
}