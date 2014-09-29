package net.brord.plugins.defaultplugin.pod;

import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.util.Vector;

/**
 * Project Default<br/>
 * Class net.brord.plugins.defaultplugin.pod.BlockInfo.java<br/>
 * @author Brord
 * @since 19 sep. 2014, 00:47:39
 */
public class BlockInfo {
	private Vector vector;
	private Material material;
	private byte durability;
	
	/**
	 * @param vector
	 * @param m
	 * @param durability
	 */
	public BlockInfo(Vector vector, Material material) {
		this(vector, material, (byte) 0);
	}
	
	/**
	 * @param vector
	 * @param m
	 * @param durability
	 */
	public BlockInfo(Vector vector, Material material, byte durability) {
		this.vector = vector;
		this.material = material;
		this.durability = durability;
	}

	/**
	 * @return the vector
	 */
	public Vector getVector() {
		return vector;
	}

	/**
	 * @return the durability
	 */
	public byte getDurability() {
		return durability;
	}

	/**
	 * @return the material
	 */
	public Material getMaterial() {
		return material;
	}
	
	public void place(World w){
		Block b = w.getBlockAt(vector.getBlockX(), vector.getBlockY(), vector.getBlockZ());
		b.setType(material);
		b.setData(durability);
	}
}
