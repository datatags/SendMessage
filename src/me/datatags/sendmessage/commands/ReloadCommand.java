package me.datatags.sendmessage.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import me.datatags.sendmessage.SendMessage;

public class ReloadCommand extends SMCommand {

    public ReloadCommand(SendMessage plugin) {
        super(plugin);
    }

    @Override
    public boolean onCommand(CommandSender sender, String[] args) {
        plugin.reloadConfig();
        sender.sendMessage(ChatColor.GREEN + "Config reloaded!");
        return true;
    }

}
