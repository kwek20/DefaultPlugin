package net.brord.plugins.defaultplugin.pod.menu;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Project Arena<br/>
 * Class net.brord.plugins.arena.pod.Menu.java<br/>
 * @author Brord
 * @since 3 aug. va, 02:40:41
 */
public abstract class Menu{
	
	private int size;
	
	protected java.util.Map<Integer, MenuItem> items = new java.util.HashMap<>();

	private String name;

	private Material locked;
	
	/**
	 * 
	 * @param size
	 * @param name
	 * @param locked
	 * @param plugin
	 */
	public Menu(int size, String name, Material locked, JavaPlugin plugin) {
		if (size < 9) size = 9;
		if (size > 45) size = 45;
		if (size % 9 != 0) size+= 9 - (size % 9);
		
		this.size = size;
		this.name = name;
		this.locked = locked;
		
		Bukkit.getServer().getPluginManager().registerEvents(new MenuListener(), plugin);
	}
	
	public boolean addItem(MenuItem item){
		for (Integer i=0; i<size; i++){
			if (!items.containsKey(i)) return addItem(i, item);
		}
		return false;
	}
	
	public boolean addItem(int spot, MenuItem item){
		if (spot < 0 || spot > size) return false;
		items.put(spot, item);
		return true;
	}
	
	public Inventory getInventory(Player p){
		Inventory inv = Bukkit.getServer().createInventory(null, size, name);
		for (Integer i : items.keySet()){
			inv.setItem(i, items.get(i).getMenuItem());
		}
		return inv;
	}
	
	/**
	 * @param item
	 */
	public void setLocked(ItemStack item, int price) {
		item.setType(locked);
		ItemMeta m = item.getItemMeta();
		java.util.List<String> lore;
		if (m.hasLore()){
			lore = m.getLore();
		} else {
			lore = new java.util.LinkedList<String>();
		}
		
		lore.add(ChatColor.DARK_RED + "Price: " + price);
		m.setLore(lore);
		item.setItemMeta(m);
	}
	
	/**
	 * @return the size
	 */
	public int getSize() {
		return size;
	}
	
	/**
	 * @return the locked
	 */
	public Material getLocked() {
		return locked;
	}
	
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * 
	 * @param entity the human who licked
	 * @param item the {@link MenuItem} clicked on
	 * @return to close the inventory or not
	 */
	public abstract boolean onClick(HumanEntity entity, MenuItem item);
	
	class MenuListener implements Listener{
		
		@EventHandler
		public void click(InventoryClickEvent e){
			if (!isInventory(e.getInventory()))return;
			if (!(e.getRawSlot() >= 0 && e.getRawSlot() < size))return;
			if (!e.getClick().isLeftClick())return;
			if (e.getCurrentItem() == null || e.getCurrentItem().getType().equals(Material.AIR))return;
			
			e.setCancelled(true);
			if (onClick(e.getWhoClicked(), items.get(e.getRawSlot()))){
				e.getWhoClicked().closeInventory();
			}
		}
		
		@EventHandler
		public void drag(InventoryDragEvent e){
			if (!isInventory(e.getInventory()))return;
			e.setCancelled(true);
		}
		
		@EventHandler
		public void close(InventoryCloseEvent e){
			if (!isInventory(e.getInventory()))return;
		}
		
		private boolean isInventory(Inventory e){
			return e.getName().equals(name) || e.getTitle().equals(name);
		}
	}
}
