package me.datatags.sendmessage.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.StringJoiner;

import me.datatags.sendmessage.SendMessage;

public abstract class SMCommand implements CommandExecutor {
    protected static final String NOT_ENOUGH_ARGS = ChatColor.RED + "Not enough arguments!";
    protected final SendMessage plugin;

    public SMCommand(SendMessage plugin) {
        this.plugin = plugin;
    }

    protected String appendAndColorize(String[] args, int startIndex) {
        StringJoiner message = new StringJoiner(" ");
        for (int i = startIndex; i < args.length; i++) {
            message.add(args[i]);
        }
        return colorize(message.toString());
    }

    protected String colorize(String str) {
        return ChatColor.translateAlternateColorCodes('&', str);
    }

    protected FileConfiguration getConfig() {
        return plugin.getConfig();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        return onCommand(sender, args);
    }

    public abstract boolean onCommand(CommandSender sender, String[] args);
}
