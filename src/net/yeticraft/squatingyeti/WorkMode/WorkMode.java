package net.yeticraft.squatingyeti.WorkMode;

import java.util.HashSet;
import java.util.Set;
import java.util.logging.Logger;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;



public class WorkMode extends JavaPlugin {
	Logger log = Logger.getLogger("Minecraft");
	public Set<String> working = new HashSet<String>();

	public void onEnable() {
		log.info ("[WorkMode] has been enabled");
		new WorkModeListener(this);
	}
	
	public void onDisable() {
		log.info ("[WorkMode] has been disabled");
	}
	
	public boolean onCommand (CommandSender sender, Command command, String label, String[] args) {
		
		Player player = (Player) sender;
		if (!(sender instanceof Player)) {
			sender.sendMessage("This command only works in game");
			return true;
		}
		
		if (args.length > 2) {
			sender.sendMessage(ChatColor.YELLOW + "Too many parameters");
			return true;
		}
		if (!player.hasPermission("work.use"))
			return true;
		
		if (label.equalsIgnoreCase("work") && (args.length == 0)) {
			player.sendMessage(ChatColor.DARK_PURPLE + "[WorkMode] " +ChatColor.GREEN+ "enabled!");
			working.add(player.getName().toLowerCase());
			player.performCommand("squat");
			player.setAllowFlight(true);
			player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 6000, 0));
			return true;
		}
		
		if (label.equalsIgnoreCase("work") && (args.length == 1)) {
			if (args[0].equalsIgnoreCase("stop")) {
				if (player.isFlying()) {
					player.sendMessage(ChatColor.YELLOW + "It's odd to see a yeti fall out of the sky, land first.");
					return true;
				}
				
				player.performCommand("stand");
				player.setAllowFlight(false);
				player.removePotionEffect(PotionEffectType.SPEED);
				player.sendMessage(ChatColor.DARK_PURPLE + "[WorkMode] " +ChatColor.RED+ "disabled!");
				working.remove(player.getName().toLowerCase());
				return true;
			}
			else {
				player.sendMessage(ChatColor.DARK_PURPLE + "WorkMode Help");
				player.sendMessage(ChatColor.YELLOW + "======================================");
				player.sendMessage(ChatColor.RED + "/work: " +ChatColor.GRAY+ "to enter WorkMode");
				player.sendMessage(ChatColor.RED + "/work stop: " +ChatColor.GRAY+ "to exit WorkMode");
				player.sendMessage(ChatColor.YELLOW + "Must not be flying to exit WorkMode");
				return true;
			}
		}
		return false;
	}
}
