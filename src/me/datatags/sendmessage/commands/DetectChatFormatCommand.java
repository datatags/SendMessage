package me.datatags.sendmessage.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.datatags.sendmessage.SendMessage;

public class DetectChatFormatCommand extends SMCommand {

    public DetectChatFormatCommand(SendMessage plugin) {
        super(plugin);
    }

    @Override
    public boolean onCommand(CommandSender sender, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "This command can only be used as a player!");
            return true;
        }
        Player player = (Player) sender;
        if (args.length == 1 && args[0].equalsIgnoreCase("setplayer")) {
            plugin.getListener().detectForPlayer(player, true);
        } else if (args.length == 0) {
            plugin.getListener().detectForPlayer(player, false);
        } else {
            player.sendMessage(ChatColor.RED + "Invalid arguments!"); 
            return false;
        }
        return true;
    }

}
