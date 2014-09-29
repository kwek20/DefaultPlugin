package net.brord.plugins.defaultplugin.util;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

/**
 * Project GhastLegionUtil<br/>
 * Class net.brord.plugins.ghastlegionutil.util.InventoryUtil.java<br/>
 * @author Brord
 * @since 4 aug. 2014, 03:02:36
 */
public class InventoryUtil {
	public static boolean isBoots(ItemStack item){
		return isBoots(item.getType());
	}
	
	public static boolean isBoots(Material material){
		return material.name().endsWith("BOOTS");
		//return material.equals(Material.DIAMOND_BOOTS) || material.equals(Material.LEATHER_BOOTS) || material.equals(Material.IRON_BOOTS) || material.equals(Material.CHAINMAIL_BOOTS) || material.equals(Material.GOLD_BOOTS);
	}
	
	public static boolean isLegs(ItemStack item){
		return isLegs(item.getType());
	}
	
	public static boolean isLegs(Material material){
		return material.name().endsWith("LEGGINGS");
	}
	
	public static boolean isChest(ItemStack item){
		return isChest(item.getType());
	}
	
	public static boolean isChest(Material material){
		return material.name().endsWith("CHESTPLATE");
	}
	
	public static boolean isHelmet(ItemStack item){
		return isHelmet(item.getType());
	}
	
	public static boolean isHelmet(Material material){
		return material.name().endsWith("HELMET"); 
	}
	
	public static boolean isArmour(ItemStack item){
		return isArmour(item.getType());
	}

	public static boolean isArmour(Material type) {
		return isBoots(type) || isLegs(type) || isChest(type) || isHelmet(type);
	}
	
	/**
	 * //TODO
	 * @param item
	 * @return [name][amount=x, damage=y, enchantments={}]
	 */
	public static String readableItem(ItemStack item){
		return (item.hasItemMeta() && item.getItemMeta().hasDisplayName() ? item.getItemMeta().getDisplayName() : item.getType().toString()) + "[" + 
				((item.getAmount() > 0) ? "amount=" + item.getAmount() + "" : "") +
				((item.hasItemMeta() && item.getItemMeta().hasEnchants()) ? "enchantments=" + item.getItemMeta().getEnchants() : "") +
				"]";
	}
}
