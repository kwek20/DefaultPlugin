package net.brord.plugins.defaultplugin.util.counter.message;

import net.brord.plugins.defaultplugin.util.StatusBarAPI;

import org.bukkit.entity.Player;

/**
 * Project GhastLegionUtil<br/>
 * Class net.brord.plugins.ghastlegionutil.util.counter.message.DragonBarMessageMode.java<br/>
 * @author Brord
 * @since 16 jul. 2014, 01:17:44
 */
public class DragonBarMessageMode implements CounterMessageMode {

	/**
	  * @see net.brord.plugins.ghastlegionutil.util.counter.message.CounterMessageMode#message(org.bukkit.entity.Player, java.lang.String)
	  */
	@Override
	public void message(Player player, String message) {
		StatusBarAPI.setStatusBar(player, message, 0);
	}

	/**
	  * @see net.brord.plugins.ghastlegionutil.util.counter.message.CounterMessageMode#clear(org.bukkit.entity.Player)
	  */
	@Override
	public void clear(Player player) {
		StatusBarAPI.removeStatusBar(player);
	}
}
