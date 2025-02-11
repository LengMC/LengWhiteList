package org.leng;

import org.bukkit.plugin.java.JavaPlugin;
import org.leng.commands.CommandHandler;
import org.leng.listeners.PlayerJoinListener;

public class LengWhiteList extends JavaPlugin {
    @Override
    public void onEnable() {
        // 保存默认配置文件
        saveDefaultConfig();
        // 注册命令处理器
        getCommand("lwl").setExecutor(new CommandHandler(this));
        // 注册事件监听器
        getServer().getPluginManager().registerEvents(new PlayerJoinListener(this), this);
        getLogger().info("LengWhiteList 已启用！");
    }

    @Override
    public void onDisable() {
        getLogger().info("LengWhiteList 已禁用！");
    }
}