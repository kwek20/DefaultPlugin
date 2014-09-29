package net.brord.plugins.defaultplugin.util.counter.message;

import org.bukkit.entity.Player;

/**
 * Project GhastLegionUtil<br/>
 * Class net.brord.plugins.ghastlegionutil.util.counter.CounterMessageMode.java<br/>
 * @author Brord
 * @since 16 jul. 2014, 01:07:03
 */
public interface CounterMessageMode {

	/**
	 * @param player
	 * @param message
	 */
	void message(Player player, String message);
	
	/**
	 * 
	 * @param player
	 */
	void clear(Player player);
}
