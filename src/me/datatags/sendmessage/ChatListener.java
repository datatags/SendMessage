package me.datatags.sendmessage;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ChatListener implements Listener {
    private final String detectChatMessage = "This is a test chat message by SendMessage.";
	private final SendMessage sm;
	private final Map<UUID,Boolean> playersDetecting = new HashMap<>();
	public ChatListener(SendMessage plugin) {
		sm = plugin;
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onEvent(AsyncPlayerChatEvent e) {
		Player player = e.getPlayer();
		if (!playersDetecting.containsKey(player.getUniqueId())) return;
		if (!e.getMessage().contains(detectChatMessage)) return;
		boolean setDefaultPlayer = playersDetecting.remove(player.getUniqueId());
        e.setCancelled(true);
        String format = e.getFormat();
        if (format.contains(player.getDisplayName())) {
            format = format.replace(player.getDisplayName(), "<player>");
            if (setDefaultPlayer) {
                sm.getConfig().set("default-player", player.getDisplayName().replaceAll(String.valueOf(ChatColor.COLOR_CHAR), "&"));
                player.sendMessage(ChatColor.GREEN + "Set default player to " + player.getDisplayName());
            }
        } else if (format.contains(player.getName())) {
            format = format.replace(player.getName(), "<player>");
            if (setDefaultPlayer) {
                sm.getConfig().set("default-player", player.getName());
                player.sendMessage(ChatColor.GREEN + "Set default player to " + player.getName());
            }
        } else if (format.contains("%1$s")) {
            format = format.replace("%1$s", "<player>");
            if (setDefaultPlayer) {
                sm.getConfig().set("default-player", player.getName());
                player.sendMessage(ChatColor.GREEN + "Set default player to " + player.getName());
            }
        } else {
            player.sendMessage(ChatColor.RED + "Could not find player name " + player.getName() + " or display name " + player.getDisplayName() + " in: " + format);
            player.sendMessage(ChatColor.RED + "If you have a nickname on, you may have an incompatible chat plugin. Please report this message to " + sm.getDescription().getAuthors().get(0) + " and/or configure manually.");
            return;
        }
        if (format.contains("%2$s")) {
            format = format.replace("%2$s", "<message>");
        } else {
            player.sendMessage(ChatColor.RED + "Could not find message spot in format '" + format + "'. Please report this message to " + sm.getDescription().getAuthors().get(0) + " and/or configure manually.");
            return;
        }
        format = format.replaceAll(String.valueOf(ChatColor.COLOR_CHAR), "&");
        sm.getConfig().set("chat-format", format);
        sm.saveConfig();
        player.sendMessage(ChatColor.GREEN + "Success! Stored result '" + format + "' in config.");
        player.sendMessage(ChatColor.YELLOW + "If this doesn't look like your chat format, please configure it manually.");
        return;
	}

	public void detectForPlayer(Player player, boolean setDefaultPlayer) {
	    playersDetecting.put(player.getUniqueId(), setDefaultPlayer);
	    player.chat(detectChatMessage);
	}
}