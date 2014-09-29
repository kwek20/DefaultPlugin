/**
 * 
 */
package net.brord.plugins.defaultplugin.util;

import net.brord.plugins.defaultplugin.Default;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

/**
 * @author Brord
 * 
 */
public class FallExplosionUtil {

	public static int radius = 20;
	public static double damage = 20;

	/**
	 * 
	 */
	public static void createFallExplosion(final Player p, final Material type,
			final byte data) {
		createFallExplosion(p, p.getLocation(), type, data);
	}

	/**
	 * @param entity
	 * @param damagee
	 * @param cause
	 * @param damage
	 * @return
	 */
	public static boolean damage(Entity entity, LivingEntity damagee,
			DamageCause cause, double damage) {
		EntityDamageByEntityEvent event = new EntityDamageByEntityEvent(entity,
				damagee, cause, damage);
		if (!event.isCancelled()) {
			damagee.damage(damage > damagee.getHealth() ? damagee.getHealth()
					: damage, entity);
		}
		return !event.isCancelled();
	}

	/**
	 * @param player
	 * @param to
	 * @param type
	 * @param data
	 */
	public static void createFallExplosion(Player p, final Location to,
			final Material type, final byte data) {

		for (Entity ent : p.getNearbyEntities(radius, radius, radius)) {
			if (ent instanceof LivingEntity) {
				if (!(ent == p)) {
					damage(p, (LivingEntity) ent, DamageCause.ENTITY_ATTACK,
							damage);
				}
			}
		}

		// shockwave code
		new BukkitRunnable() {
			int amount = 0;

			@Override
			public void run() {
				if (amount >= 9)
					cancel();
				for (double i = -(2 * Math.PI); i < 2 * Math.PI; i += Math
						.toRadians(360 / 36)) {
					FallingBlock b = to.getWorld().spawnFallingBlock(to, type,
							data);
					b.setMetadata("Killstreaks",
							new FixedMetadataValue(Default.getInstance(),
									"shockwave"));
					b.setVelocity(new Vector(Math.cos(i) / 2, 0.4,
							Math.sin(i) / 2));
				}
				amount++;
			}
		}.runTaskTimer(Default.getInstance(), 0, 1);
	}

}
