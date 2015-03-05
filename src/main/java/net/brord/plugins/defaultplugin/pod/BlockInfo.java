package net.brord.plugins.defaultplugin.pod;

import net.brord.plugins.defaultplugin.interfaces.Managable;

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
public class BlockInfo implements Managable{
	
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

	/**
	 * x:y:z:anythingbutair:anythingbut0
	  * @see net.brord.plugins.ghastlegionutil.interfaces.Managable#name()
	  */
	@Override
	public String name() {
		return getVector().getBlockX() + ":" +getVector().getBlockY() + ":" +getVector().getBlockZ() + 
				(!getMaterial().equals(Material.AIR) ? ":" + getMaterial() : "") + 
				(getDurability() != 0 ? ":" + getDurability() : "");
	}
	
	/**
	  * @see java.lang.Object#toString()
	  */
	@Override
	public String toString() {
		return name();
	}
	
	/**
	 * parses a string in a blockinfo
	 * @param s
	 * @return
	 */
	public static BlockInfo parse(String s){
		String[] splits = s.split(":");
		if (splits.length < 3) return null;
		int[] ints = new int[3];
		
		try {
			for (int i=0; i<3; i++){
				ints[i] = Integer.parseInt(splits[i]);
			}
		} catch (NumberFormatException e){
			return null;
		}
		
		Material m = splits.length > 3 ? Material.getMaterial(splits[3]) : Material.AIR;
		Byte b = splits.length > 4 ? Byte.parseByte(splits[4]) : 0;
		return new BlockInfo(new Vector(ints[0], ints[1], ints[2]), m, b);
	}
}
