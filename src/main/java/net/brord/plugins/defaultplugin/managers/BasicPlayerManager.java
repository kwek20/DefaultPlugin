package net.brord.plugins.defaultplugin.managers;

import java.util.List;

import net.brord.plugins.defaultplugin.BasicPlugin;
import net.brord.plugins.defaultplugin.datastore.BasicPlayer;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

/**
 * Project Default<br/>
 * Class net.brord.plugins.defaultplugin.managers.BasicPlayerManager.java<br/>
 * @author Brord
 * @since 25 jun. 2014, 14:11:50
 */
public abstract class BasicPlayerManager<T extends BasicPlayer> extends Manager<T>{

	
	/**
	 * 
	 */
	public BasicPlayerManager(Class<T> type, BasicPlugin plugin) {
		super(type);
		Bukkit.getServer().getPluginManager().registerEvents(new BasicPlayerManagerListener(), plugin);
		for (Player p : Bukkit.getServer().getOnlinePlayers()){
			onJoin(p);
		}
	}
	
	public void removePlayer(Player player){
		removePlayer(player.getName());
	}
	
	/**
	 * @return the players
	 */
	public List<T> getPlayers() {
		return data;
	}
	
	public void removePlayer(String name){
		T p = null;
		for (T player : data){
			if (player.getName().equals(name)){
				p = player;
				break;
			}
		}
		
		if (p != null){
			remove(p);
		}
	}
	
	public abstract void onJoin(Player p);
	public abstract void onLeave(Player p);
	
	private class BasicPlayerManagerListener implements Listener{

		@EventHandler(priority = EventPriority.MONITOR)
		public void join(PlayerJoinEvent e){
			onJoin(e.getPlayer());
		}
		
		@EventHandler(priority = EventPriority.MONITOR)
		public void leave(PlayerQuitEvent e){
			onLeave(e.getPlayer());
		}
	}
}
