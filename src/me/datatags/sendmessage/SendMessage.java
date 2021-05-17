package me.datatags.sendmessage;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.permissions.Permission;
import org.bukkit.plugin.java.JavaPlugin;



public class SendMessage extends JavaPlugin {
	
	public Permission   senderPermission     = new Permission("sendmessage.use");
	public Permission   broadcastPermission  = new Permission("sendmessage.broadcast");
	public Permission   chatPermission       = new Permission("sendmessage.chat");
	public Permission   reloadPermission     = new Permission("sendmessage.reload");
	public Permission   colorsPermission     = new Permission("sendmessage.colors");
	public Permission   detectChatPermission = new Permission("sendmessage.detectchatformat");
	public String       noPermissionMessage  = ChatColor.RED + "You do not have permission to use this command.";
	public String       notEnoughArgsMessage = ChatColor.RED + "Not enough arguments!";
	public String       detectChatMessage    = "This is a test chat message by SendMessage.";
	public String       dcfSetPlayerMessage  = "This is a test chat message by SendMessage. And don't forget to set the default player too.";
	public List<String> playersDetecting     = new ArrayList<String>();
	
	@Override
	public void onEnable() {
		// Now that we're registering permissions in plugin.yml now instead, this is not necessary.
		/*PluginManager pm = getServer().getPluginManager();
		pm.addPermission(senderPermission);
		pm.addPermission(broadcastPermission);
		pm.addPermission(chatPermission);
		pm.addPermission(reloadPermission);
		pm.addPermission(colorsPermission);
		pm.addPermission(detectChatPermission);*/
		
		this.getConfig().addDefault("chat-format", "<<player>>  <message>");
		this.getConfig().addDefault("default-player", "DefaultPlayer");
		this.getConfig().options().copyDefaults(true);
		saveConfig();
		new ChatListener(this);
	}
	
	@Override
	public void onDisable() {
		
	}
	String coloredMessage;
	String appendedMessage;
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		if (cmd.getName().equalsIgnoreCase("sendmsg")) {
			if (args.length > 1) {
				if (sender.hasPermission(senderPermission)) {
					for (Player playerToMsg : Bukkit.getServer().getOnlinePlayers()) {
						if (playerToMsg.getName().equalsIgnoreCase(args[0])) {
							appendedMessage = "";
							for (int i = 1; i < args.length; i++) {
								coloredMessage = ChatColor.translateAlternateColorCodes('&', args[i]);
								appendedMessage += coloredMessage + ' ';
							}
							playerToMsg.sendMessage(appendedMessage);
							sender.sendMessage(ChatColor.GREEN + "Message successfully sent to: " + ChatColor.GOLD + ChatColor.BOLD + playerToMsg.getName() + ChatColor.GREEN + "!");
							return true;
						}
					}
					sender.sendMessage(ChatColor.RED + "Player not found!"); // If we got this far without returning, we didn't find it so no need for a variable.
					return true;
				
				} else {
					sender.sendMessage(noPermissionMessage);
					return true;
				}
			} else {
				sender.sendMessage(notEnoughArgsMessage);
				return false;
			}
		
		} else if (cmd.getName().equalsIgnoreCase("sendbc")) {
			if (sender.hasPermission(broadcastPermission)) {
				if (args.length > 0) {
					appendedMessage = "";
					for (int i = 0; i < args.length; i++) {
						coloredMessage = ChatColor.translateAlternateColorCodes('&', args[i]);
						appendedMessage += coloredMessage + ' ';
					}
					Bukkit.broadcastMessage(appendedMessage);
					sender.sendMessage(ChatColor.GREEN + "Broadcast successfully sent!");
				} else {
					sender.sendMessage(notEnoughArgsMessage);
					return false;
				}
			} else {
				sender.sendMessage(noPermissionMessage);
			}
			return true;
		} else if (cmd.getName().equalsIgnoreCase("sendchat")) {
			if (sender.hasPermission(chatPermission)) {
				if (args.length > 0) {
					int initialOffset = 0;
					String playerName = "";
					if (args[0].equalsIgnoreCase("-p")) {
						if (args.length < 3) {
							sender.sendMessage(notEnoughArgsMessage);
							return false;
						}
						playerName = args[1];
						initialOffset = 2;
					} else {
						playerName = ChatColor.translateAlternateColorCodes('&', this.getConfig().getString("default-player"));
					}
					appendedMessage = "";
					for (int i = initialOffset; i < args.length; i++) {
						coloredMessage = ChatColor.translateAlternateColorCodes('&', args[i]);
						appendedMessage += coloredMessage + ' ';
					}
					Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', this.getConfig().getString("chat-format")).replace("<player>", ChatColor.translateAlternateColorCodes('&', playerName)).replace("<message>", appendedMessage));
					sender.sendMessage(ChatColor.GREEN + "Chat message successfully sent!");
				} else {
					sender.sendMessage(notEnoughArgsMessage);
					return false;
				}
			} else {
				sender.sendMessage(noPermissionMessage);
			}
			return true;
		} else if (cmd.getName().equalsIgnoreCase("smreload")) {
			if (sender.hasPermission(reloadPermission)) {
				sender.sendMessage("Reloading config...");
				reloadConfig();
				sender.sendMessage("Reload complete!");
			} else {
				sender.sendMessage(noPermissionMessage);
			}
			return true;
		} else if (cmd.getName().equalsIgnoreCase("colors")) {
			if (sender.hasPermission(colorsPermission)) {
				sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "The colors are:  &00&11&22&33&44&55&66&77&88&99&aa&bb&cc&dd&ee&ff k=&kk&r &ll&r&mm&r&nn&r&oo"));
			} else {
				sender.sendMessage(noPermissionMessage);
			}
			return true;
		} else if (cmd.getName().equalsIgnoreCase("detectchatformat")) {
			if (sender.hasPermission(detectChatPermission)) {
				if (sender instanceof Player) {
					Player player = (Player) sender;
					if (args.length == 1 && args[0].equalsIgnoreCase("setplayer")) {
						playersDetecting.add(player.getName());
						player.chat(dcfSetPlayerMessage);
					} else if (args.length == 0) {
						playersDetecting.add(player.getName());
						player.chat(detectChatMessage);
					} else {
						player.sendMessage(ChatColor.RED + "Invalid arguments!"); //Not using notEnoughArgsMessage here because the outcome could be that the user has specified more than one argument and/or an incorrect one. 
						return false;
					}
				} else {
					sender.sendMessage(ChatColor.RED + "This command can only be used as a player!");
				}
			} else {
				sender.sendMessage(noPermissionMessage);
			}
			return true;
		}
		return false;
			
	}
	
}