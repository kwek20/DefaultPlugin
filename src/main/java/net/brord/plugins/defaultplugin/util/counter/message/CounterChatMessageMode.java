package net.brord.plugins.defaultplugin.util.counter.message;

import org.bukkit.entity.Player;

/**
 * Project GhastLegionUtil<br/>
 * Class net.brord.plugins.ghastlegionutil.util.counter.CounterChatMessageMode.java<br/>
 * @author Brord
 * @since 16 jul. 2014, 01:08:11
 */
public class CounterChatMessageMode implements CounterMessageMode {

	/**
	 * @see net.brord.plugins.ghastlegionutil.util.counter.message.CounterMessageMode#message(org.bukkit.entity.Player)
	 */
	@Override
	public void message(Player player, String message) {
		player.sendMessage(message);
	}

	/**
	  * @see net.brord.plugins.ghastlegionutil.util.counter.message.CounterMessageMode#clear(org.bukkit.entity.Player)
	  */
	@Override
	public void clear(Player player) {
		
	}

}
