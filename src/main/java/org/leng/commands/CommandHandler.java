package org.leng.commands;

import org.leng.LengWhiteList;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.ChatColor;

public class CommandHandler implements CommandExecutor {
    private final LengWhiteList plugin;

    public CommandHandler(LengWhiteList plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 0) {
            sender.sendMessage(ChatColor.RED + "用法: /lwl <add|remove|list|reload> [玩家名]");
            return true;
        }

        switch (args[0].toLowerCase()) {
            case "add":
                if (!sender.hasPermission("lwl.add")) {
                    sender.sendMessage(ChatColor.RED + "你没有权限添加玩家到白名单。");
                    return true;
                }
                if (args.length < 2) {
                    sender.sendMessage(ChatColor.RED + "用法: /lwl add <玩家名>");
                    return true;
                }
                return handleAddCommand(sender, args[1]);
            case "remove":
                if (!sender.hasPermission("lwl.remove")) {
                    sender.sendMessage(ChatColor.RED + "你没有权限从白名单中移除玩家。");
                    return true;
                }
                if (args.length < 2) {
                    sender.sendMessage(ChatColor.RED + "用法: /lwl remove <玩家名>");
                    return true;
                }
                return handleRemoveCommand(sender, args[1]);
            case "list":
                if (!sender.hasPermission("lwl.list")) {
                    sender.sendMessage(ChatColor.RED + "你没有权限查看白名单。");
                    return true;
                }
                return handleListCommand(sender);
            case "reload":
                if (!sender.hasPermission("lwl.reload")) {
                    sender.sendMessage(ChatColor.RED + "你没有权限重载配置。");
                    return true;
                }
                return handleReloadCommand(sender);
            default:
                sender.sendMessage(ChatColor.RED + "无效的子命令。用法: /lwl <add|remove|list|reload> [玩家名]");
                return true;
        }
    }

    private boolean handleAddCommand(CommandSender sender, String playerName) {
        FileConfiguration config = plugin.getConfig();
        if (config.getBoolean("whitelist." + playerName, false)) {
            sender.sendMessage(ChatColor.YELLOW + "玩家 " + playerName + " 已经在白名单中。");
        } else {
            config.set("whitelist." + playerName, true);
            plugin.saveConfig();
            sender.sendMessage(ChatColor.GREEN + "玩家 " + playerName + " 已添加到白名单。");
        }
        return true;
    }

    private boolean handleRemoveCommand(CommandSender sender, String playerName) {
        FileConfiguration config = plugin.getConfig();
        if (!config.getBoolean("whitelist." + playerName, false)) {
            sender.sendMessage(ChatColor.YELLOW + "玩家 " + playerName + " 不在白名单中。");
        } else {
            config.set("whitelist." + playerName, null);
            plugin.saveConfig();
            sender.sendMessage(ChatColor.GREEN + "玩家 " + playerName + " 已从白名单中移除。");

            // 检查玩家是否在线，并将其踢出服务器
            Player player = plugin.getServer().getPlayer(playerName);
            if (player != null) {
                player.kickPlayer(ChatColor.RED + "你已被移出白名单。");
            }
        }
        return true;
    }

    private boolean handleListCommand(CommandSender sender) {
        FileConfiguration config = plugin.getConfig();
        if (config.getConfigurationSection("whitelist") == null || config.getConfigurationSection("whitelist").getKeys(false).isEmpty()) {
            sender.sendMessage(ChatColor.YELLOW + "白名单为空。");
        } else {
            sender.sendMessage(ChatColor.GREEN + "白名单：");
            for (String player : config.getConfigurationSection("whitelist").getKeys(false)) {
                sender.sendMessage(player);
            }
        }
        return true;
    }

    private boolean handleReloadCommand(CommandSender sender) {
        plugin.reloadConfig();
        sender.sendMessage(ChatColor.GREEN + "配置已重载。");
        return true;
    }
}