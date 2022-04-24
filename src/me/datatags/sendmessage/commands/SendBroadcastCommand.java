package me.datatags.sendmessage.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import me.datatags.sendmessage.SendMessage;

public class SendBroadcastCommand extends SMCommand {

    public SendBroadcastCommand(SendMessage plugin) {
        super(plugin);
    }

    @Override
    public boolean onCommand(CommandSender sender, String[] args) {
        if (args.length < 1) {
            sender.sendMessage(NOT_ENOUGH_ARGS);
            return false;
        }
        Bukkit.broadcastMessage(appendAndColorize(args, 0));
        sender.sendMessage(ChatColor.GREEN + "Broadcast successfully sent!");
        return true;
    }

}
