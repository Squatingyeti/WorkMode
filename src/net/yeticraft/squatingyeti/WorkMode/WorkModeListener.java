package net.yeticraft.squatingyeti.WorkMode;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;

public class WorkModeListener implements Listener {
	public static WorkMode pl;
	
	public WorkModeListener(WorkMode plugin) {
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
        pl = plugin;
    } 
	
	@EventHandler(priority = EventPriority.NORMAL)
	public void onEntityDamage(EntityDamageEvent event) {
		if (event.isCancelled() || (event.getDamage() == 0)) return;
		
		if (!(event instanceof EntityDamageByEntityEvent)) return;
		
		EntityDamageByEntityEvent damageByEntityEvent = (EntityDamageByEntityEvent) event;
		
		Entity attacker = damageByEntityEvent.getDamager();
		Entity defender = damageByEntityEvent.getEntity();
		
		if (attacker instanceof Projectile)
			attacker = ((Projectile)attacker).getShooter();
		
		if (!(defender instanceof Player && attacker instanceof Player)) return;
			
			Player defenderPlayer = (Player) defender;
			defenderPlayer.sendMessage("You are defending an attack!");
			Player attackerPlayer = (Player) attacker;
			attackerPlayer.sendMessage("You attacked another player!");
		
		if (!pl.working.contains(attackerPlayer.getName().toLowerCase()) || 
				(!pl.working.contains(defenderPlayer.getName().toLowerCase()))){
			attackerPlayer.sendMessage("Not in the working set");
			defenderPlayer.sendMessage("Not in the working set");
			return;
		}
		event.setCancelled(true);
		event.setDamage(0);
	}

}
