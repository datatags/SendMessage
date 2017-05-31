package me.AlanZ;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class ChatListener implements Listener {
	
	SendMessage sm;
	public ChatListener(SendMessage plugin) {
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
		this.sm = plugin;
	}
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onEvent(AsyncPlayerChatEvent e) {
		Player player = e.getPlayer();
		for (String pname : sm.playersDetecting) {
			if (player.getName() == pname) {
				sm.playersDetecting.remove(player.getName());
				e.setCancelled(true);
				String format = e.getFormat();
				if (format.contains(player.getDisplayName())) {
					format = format.replace(player.getDisplayName(), "<player>");
					if (e.getMessage().contains("player")) {
						sm.getConfig().set("default-player", player.getDisplayName().replaceAll(String.valueOf(ChatColor.COLOR_CHAR), "&"));
					}
					player.sendMessage(ChatColor.GREEN + "Set default player to " + player.getDisplayName());
				} else if (format.contains(player.getName())) {
					format = format.replace(player.getName(), "<player>");
					if (e.getMessage().contains("player")) {
						sm.getConfig().set("default-player", player.getName());
					}
					player.sendMessage(ChatColor.GREEN + "Set default player to " + player.getName());
				} else if (format.contains("%1$s")) {
					format = format.replace("%1$s", "<player>");
					if (e.getMessage().contains("player")) {
						sm.getConfig().set("default-player", player.getName());
					}
					player.sendMessage(ChatColor.GREEN + "Set default player to " + player.getName());
				} else {
					player.sendMessage(ChatColor.RED + "Could not find player name " + player.getName() + " or display name " + player.getDisplayName() + " in:  " + format);
					player.sendMessage(ChatColor.RED + "If you have a nickname on, you may have an incompatible chat plugin.  Please report this message to AlanZ and/or configure manually.");
					return;
				}
				if (format.contains("%2$s")) {
					format = format.replace("%2$s", "<message>");
				} else {
					player.sendMessage(ChatColor.RED + "Could not find message spot in format " + format + "  Please report this message to AlanZ and/or configure manually.");
					return;
				}
				format = format.replaceAll(String.valueOf(ChatColor.COLOR_CHAR), "&");
				player.sendMessage(ChatColor.GREEN + "Success!  Storing result:  " + format + " in config...");
				sm.getConfig().set("chat-format", format);
				sm.saveConfig();
				sm.reloadConfig();
				return;
			}
		}
	}
	
}