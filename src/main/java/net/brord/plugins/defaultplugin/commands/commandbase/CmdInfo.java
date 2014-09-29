/**
 * 
 */
package net.brord.plugins.defaultplugin.commands.commandbase;

import net.brord.plugins.defaultplugin.BasicPlugin;
import net.brord.plugins.defaultplugin.commands.commandutils.CommandBase;
import net.brord.plugins.defaultplugin.util.Parse;

import org.bukkit.ChatColor;

/**
 * @author Brord 
 *
 */
public class CmdInfo extends CommandBase {
	
	/**
	 * @param sender
	 * @param command
	 * @param args
	 * @param permission
	 */
	public CmdInfo(String command, BasicPlugin plugin) {
		super(command, plugin);
	}

	/** 
	 * @see net.castegaming.plugins.commands.commandutils.brord.rschallenge.commands.CommandBase#handle()
	 */
	@Override
	public String handle() {
		msg(ChatColor.GRAY + "--------------" + getPlugin().getPrefix() + ChatColor.GRAY + "--------------");
		msg(ChatColor.GOLD + "Welcome to " + getPlugin().getName());
		msg(ChatColor.GOLD + getPlugin().getDescription().getDescription());
		msg(ChatColor.GOLD + "For a list of all our commands, type /" + getPlugin().getName().toLowerCase() + " help");
		msg(ChatColor.GOLD + "Version: " + getPlugin().getDescription().getVersion());
		msg(ChatColor.GOLD + "Authors: " + Parse.arrayToString(getPlugin().getDescription().getAuthors().toArray()));
		return null;
	}
}
