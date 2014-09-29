package net.brord.plugins.defaultplugin.util;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;

/**
 * Project Default<br/>
 * Class net.brord.plugins.defaultplugin.util.Checkers.java<br/>
 * @author Brord
 * @since 26 jun. 2014, 16:50:16
 */
public class Checkers {
	/**
	 * Checks if this block is safe to drop another block on
	 * @param loc
	 * @return
	 */
	public static boolean safeToDrop(Location loc) {
		Block b = loc.getBlock();
		if (b.getType().equals(Material.SAPLING))
			return false;
		if (b.getType().equals(Material.RAILS))
			return false;
		if (b.getType().equals(Material.POWERED_RAIL))
			return false;
		if (b.getType().equals(Material.DETECTOR_RAIL))
			return false;
		if (b.getType().equals(Material.WEB))
			return false;
		if (b.getType().equals(Material.YELLOW_FLOWER))
			return false;
		if (b.getType().equals(Material.RED_ROSE))
			return false;
		if (b.getType().equals(Material.DOUBLE_PLANT))
			return false;
		if (b.getType().equals(Material.BROWN_MUSHROOM))
			return false;
		if (b.getType().equals(Material.RED_MUSHROOM))
			return false;
		if (b.getType().equals(Material.STEP))
			return false;
		if (b.getType().equals(Material.TORCH))
			return false;
		if (b.getType().equals(Material.LEVER))
			return false;
		if (b.getType().equals(Material.STONE_PLATE))
			return false;
		if (b.getType().equals(Material.WOOD_PLATE))
			return false;
		if (b.getType().equals(Material.IRON_PLATE))
			return false;
		if (b.getType().equals(Material.GOLD_PLATE))
			return false;
		if (b.getType().equals(Material.REDSTONE_TORCH_OFF))
			return false;
		if (b.getType().equals(Material.REDSTONE_TORCH_ON))
			return false;
		if (b.getType().equals(Material.STONE_BUTTON))
			return false;
		if (b.getType().equals(Material.WOOD_BUTTON))
			return false;
		if (b.getType().equals(Material.SOUL_SAND))
			return false;
		if (b.getType().equals(Material.TRAP_DOOR))
			return false;
		if (b.getType().equals(Material.WOOD_STEP))
			return false;
		if (b.getType().equals(Material.TRIPWIRE_HOOK))
			return false;
		if (b.getType().equals(Material.TRIPWIRE))
			return false;
		if (b.getType().equals(Material.DAYLIGHT_DETECTOR))
			return false;
		if (b.getType().equals(Material.SKULL))
			return false;
		if (b.isLiquid() || b.getRelative(BlockFace.UP).isLiquid() || b.getRelative(BlockFace.DOWN).isLiquid())
			return false;
		if (b.getType().hasGravity() || b.getType().equals(Material.ICE)) {
			//plugin.dropLocation.add(0.0D, 1.0D, 0.0D);
			return true;
		} else {
			return true;
		}
	}
}
