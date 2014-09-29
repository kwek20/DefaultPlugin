package net.brord.plugins.defaultplugin.pod.menu;

import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

/**
 * Project Arena<br/>
 * Class net.brord.plugins.arena.pod.MenuItem.java<br/>
 * @author Brord
 * @since 3 aug. 2014, 15:50:00
 */
public class MenuItem {
	private ItemStack item;
	private String name;
	private int price;

	/**
	 * 
	 * @param name
	 * @param item
	 * @param price set to -1 to be ignored
	 */
	public MenuItem(String name, ItemStack item, int price) {
		this.name = name;
		this.item = item;
		this.price = price;
	}
	
	/**
	 * @return the item
	 */
	public ItemStack getItem() {
		return item;
	}
	
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * @return the price
	 */
	public int getPrice() {
		return price;
	}

	/**
	 * @return
	 */
	public ItemStack getMenuItem() {
		ItemStack item = this.item.clone();
		ItemMeta m = item.getItemMeta();
		m.setDisplayName(name);
		item.setItemMeta(m);
		return item;
	}
}
