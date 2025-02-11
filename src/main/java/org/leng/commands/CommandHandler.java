package org.leng.commands;

import org.leng.LengWhiteList;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;

public class CommandHandler implements CommandExecutor {
    private final LengWhiteList plugin;

    public CommandHandler(LengWhiteList plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 0) {
            sender.sendMessage("Usage: /lwl <add|remove|list> [player]");
            return true;
        }

        switch (args[0].toLowerCase()) {
            case "add":
                if (!sender.hasPermission("lwl.add")) {
                    sender.sendMessage("You do not have permission to add players to the whitelist.");
                    return true;
                }
                if (args.length < 2) {
                    sender.sendMessage("Usage: /lwl add <player>");
                    return true;
                }
                plugin.getConfig().set("whitelist." + args[1], true);
                plugin.saveConfig();
                sender.sendMessage("Player " + args[1] + " added to whitelist.");
                return true;
            case "remove":
                if (!sender.hasPermission("lwl.remove")) {
                    sender.sendMessage("You do not have permission to remove players from the whitelist.");
                    return true;
                }
                if (args.length < 2) {
                    sender.sendMessage("Usage: /lwl remove <player>");
                    return true;
                }
                plugin.getConfig().set("whitelist." + args[1], null);
                plugin.saveConfig();
                sender.sendMessage("Player " + args[1] + " removed from whitelist.");
                return true;
            case "list":
                if (!sender.hasPermission("lwl.list")) {
                    sender.sendMessage("You do not have permission to view the whitelist.");
                    return true;
                }
                FileConfiguration config = plugin.getConfig();
                if (config.getConfigurationSection("whitelist") == null || config.getConfigurationSection("whitelist").getKeys(false).isEmpty()) {
                    sender.sendMessage("Whitelist is empty.");
                } else {
                    sender.sendMessage("Whitelist:");
                    for (String player : config.getConfigurationSection("whitelist").getKeys(false)) {
                        sender.sendMessage(player);
                    }
                }
                return true;
            default:
                sender.sendMessage("Invalid subcommand. Usage: /lwl <add|remove|list> [player]");
                return true;
        }
    }
}