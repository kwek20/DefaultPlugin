/**
 * 
 */
package net.brord.plugins.defaultplugin.commands.commandbase;

import net.brord.plugins.defaultplugin.BasicPlugin;
import net.brord.plugins.defaultplugin.commands.commandutils.CommandBase;
import net.brord.plugins.defaultplugin.interfaces.ConsoleCommand;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

/**
 * @author Brord
 *
 */
public class CmdConsole extends CommandBase implements ConsoleCommand {

	/**
	 * 
	 * @param sender
	 * @param command
	 * @param args
	 */
	public CmdConsole(String command, BasicPlugin plugin){
		super(command, plugin);
	}

	@Override
	public String handle() {
		if (args.length < 3){
			return null;
		}
		
		Player p = Bukkit.getServer().getPlayer(args[0]);
		if (p == null){
			return null;
		}
		
		int amount;
		try {
			amount = Integer.parseInt(args[1]);
		} catch (NumberFormatException e){
			return null;
		}
		
		return null;
	}

}
