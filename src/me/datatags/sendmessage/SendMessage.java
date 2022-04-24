package me.datatags.sendmessage;

import org.bukkit.plugin.java.JavaPlugin;

import me.datatags.sendmessage.commands.*;

public class SendMessage extends JavaPlugin {

	private ChatListener chatListener;
	
	@Override
	public void onEnable() {
		getCommand("sendmsg").setExecutor(new SendMessageCommand(this));
		getCommand("sendbc").setExecutor(new SendBroadcastCommand(this));
		getCommand("sendchat").setExecutor(new SendChatCommand(this));
		getCommand("smreload").setExecutor(new ReloadCommand(this));
		getCommand("colors").setExecutor(new ColorsCommand(this));
		getCommand("detectchatformat").setExecutor(new DetectChatFormatCommand(this));
		getCommand("sendtitle").setExecutor(new SendTitleCommand(this));
		getConfig().addDefault("chat-format", "<<player>> <message>");
		getConfig().addDefault("default-player", "DefaultPlayer");
		getConfig().options().copyDefaults(true);
		saveConfig();
		chatListener = new ChatListener(this);
		getServer().getPluginManager().registerEvents(chatListener, this);
	}

	public ChatListener getListener() {
	    return chatListener;
	}
}