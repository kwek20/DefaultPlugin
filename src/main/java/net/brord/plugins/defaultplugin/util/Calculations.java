/**
 * 
 */
package net.brord.plugins.defaultplugin.util;

import java.util.Iterator;
import java.util.List;

import net.brord.plugins.defaultplugin.Default;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.util.BlockIterator;
import org.bukkit.util.Vector;

/**
 * @author Brord
 * 
 */
public class Calculations {

	public void lol(Location l, int blocks, int id) {
		for (double i = -(2 * Math.PI); i < 2 * Math.PI; i += Math
				.toRadians(360 / blocks)) {

			Location temp = l.add(Math.cos(i), 0,
					Math.sin(i) - Math.sin(Math.toRadians(360 / blocks)));

			FallingBlock b = l.getWorld().spawnFallingBlock(temp, id, (byte) 0);
			b.setMetadata("fallingShooterBlock",
					new FixedMetadataValue(Default.getInstance(), ""));
			b.setVelocity(new Vector(0, 0.4, 0));
		}
	}

	void getTarget(Player player) {
		BlockIterator bi = new BlockIterator(player.getLocation().getWorld(),
				player.getLocation().toVector(), player.getLocation()
						.getDirection(), 2, 25);
		List<Entity> list = player.getNearbyEntities(20, 20, 20);
		while (bi.hasNext()) {
			Block b = bi.next();
			Iterator<Entity> it = list.iterator();
			while (it.hasNext()) {
				Entity e = it.next();
				if (e.getType().equals(EntityType.PLAYER)) {
					if (b.getLocation().distance(e.getLocation()) <= 2
							&& player.hasLineOfSight(e)) {
						// Do stuff with the player (e) here
					}
				}
			}
		}
	}
}
