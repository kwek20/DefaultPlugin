package net.brord.plugins.defaultplugin.listeners;

import net.brord.plugins.defaultplugin.BasicPlugin;

import org.bukkit.event.Listener;

/**
 * The listener for the plugin
 * 
 * @author Brord
 * 
 */
public class DefaultListener implements Listener {

	private BasicPlugin plugin;

	public DefaultListener(BasicPlugin plugin) {
		this.plugin = plugin;
	}

	public void stop() {

	}

}
