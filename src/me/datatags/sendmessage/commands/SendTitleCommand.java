package me.datatags.sendmessage.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.datatags.sendmessage.SendMessage;
import me.datatags.sendmessage.xseries.Titles;
import net.md_5.bungee.api.ChatColor;

public class SendTitleCommand extends SMCommand {

    public SendTitleCommand(SendMessage plugin) {
        super(plugin);
    }

    @Override
    public boolean onCommand(CommandSender sender, String[] args) {
        if (args.length < 5) {
            sender.sendMessage(NOT_ENOUGH_ARGS);
            return false;
        }
        Player target = Bukkit.getPlayer(args[0]);
        if (target == null) {
            sender.sendMessage(ChatColor.RED + "Invalid player!");
            return true;
        }
        String title = colorize(args[1].replace('_', ' '));
        String subtitle = null;
        int fadeIn;
        int duration;
        int fadeOut;
        int fadeInIndex = 2;
        if (args.length == 6) {
            subtitle = colorize(args[2].replace('_', ' '));
            fadeInIndex = 3;
        }
        try {
            fadeIn = Integer.parseInt(args[fadeInIndex]);
            duration = Integer.parseInt(args[fadeInIndex + 1]);
            fadeOut = Integer.parseInt(args[fadeInIndex + 2]);
        } catch (NumberFormatException e) {
            sender.sendMessage(ChatColor.RED + "Invalid number!");
            return false;
        }
        Titles.sendTitle(target, fadeIn, duration, fadeOut, title, subtitle);
        sender.sendMessage(ChatColor.GREEN + "Success!");
        return true;
    }

}
