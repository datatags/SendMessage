package me.datatags.sendmessage.commands;

import org.bukkit.command.CommandSender;

import me.datatags.sendmessage.SendMessage;

public class ColorsCommand extends SMCommand {

    public ColorsCommand(SendMessage plugin) {
        super(plugin);
    }

    @Override
    public boolean onCommand(CommandSender sender, String[] args) {
        sender.sendMessage(colorize("The colors are:  &00&11&22&33&44&55&66&77&88&99&aa&bb&cc&dd&ee&ff k=&kk&r &ll&r&mm&r&nn&r&oo"));
        return true;
    }

}
