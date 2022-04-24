package me.datatags.sendmessage.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import me.datatags.sendmessage.SendMessage;

public class SendChatCommand extends SMCommand {

    public SendChatCommand(SendMessage plugin) {
        super(plugin);
    }

    @Override
    public boolean onCommand(CommandSender sender, String[] args) {
        if (args.length < 1) {
            sender.sendMessage(NOT_ENOUGH_ARGS);
            return false;
        }
        int initialOffset = 0;
        String playerName = "";
        if (args[0].equalsIgnoreCase("-p")) {
            if (args.length < 3) {
                sender.sendMessage(NOT_ENOUGH_ARGS);
                return false;
            }
            playerName = args[1];
            initialOffset = 2;
        } else {
            playerName = getConfig().getString("default-player");
        }
        String broadcast = colorize(getConfig().getString("chat-format"));
        broadcast = broadcast.replace("<player>", colorize(playerName));
        broadcast = broadcast.replace("<message>", appendAndColorize(args, initialOffset));
        Bukkit.broadcastMessage(broadcast);
        sender.sendMessage(ChatColor.GREEN + "Chat message successfully sent!");
        return true;
    }

}
