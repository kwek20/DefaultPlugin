package net.brord.plugins.defaultplugin.listeners;


import net.brord.plugins.defaultplugin.BasicPlugin;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;

/**
 * The listener for the plugin
 * 
 * @author Brord
 * 
 */
public class DefaultListenerImplement extends DefaultListener implements
		Listener {

	public DefaultListenerImplement(BasicPlugin plugin) {
		super(plugin);
	}

	/**
	 * Example listener function
	 * 
	 * @param e
	 */
	@EventHandler
	public void login(PlayerLoginEvent e) {

	}
}
