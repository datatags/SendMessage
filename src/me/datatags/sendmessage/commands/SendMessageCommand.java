package me.datatags.sendmessage.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.datatags.sendmessage.SendMessage;

public class SendMessageCommand extends SMCommand {

    public SendMessageCommand(SendMessage plugin) {
        super(plugin);
    }

    @Override
    public boolean onCommand(CommandSender sender, String[] args) {
        if (args.length < 2) {
            sender.sendMessage(NOT_ENOUGH_ARGS);
            return false;
        }
        Player player = Bukkit.getPlayer(args[0]);
        if (player == null) {
            sender.sendMessage(ChatColor.RED + "Player not found!");
            return true;
        }

        player.sendMessage(appendAndColorize(args, 1));
        sender.sendMessage(ChatColor.GREEN + "Message successfully sent to: " + ChatColor.GOLD + ChatColor.BOLD + player.getName() + ChatColor.GREEN + "!");
        return true;
    }

}
