package net.brord.plugins.defaultplugin.datastore;

import java.util.UUID;

import net.brord.plugins.defaultplugin.interfaces.Managable;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

/**
 * Project Default<br/>
 * Class net.brord.plugins.defaultplugin.datastore.BasicPlayer.java<br/>
 * @author Brord
 * @since 25 jun. 2014, 13:52:23
 */
public abstract class BasicPlayer implements Managable{

	private String name;
	private UUID uuid;

	/**
	 * 
	 */
	public BasicPlayer(Player player) {
		this.uuid = player.getUniqueId();
		this.name = player.getName();
	}
	
	public Player getPlayer(){
		return Bukkit.getServer().getPlayer(uuid);
	}
	
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * @return the uuid
	 */
	public UUID getUuid() {
		return uuid;
	}
	
	/**
	  * @see net.brord.plugins.defaultplugin.interfaces.Managable#name()
	  */
	@Override
	public String name() {
		return name;
	}
}
