/**
 * 
 */
package net.brord.plugins.defaultplugin.commands.commandbase;

import net.brord.plugins.defaultplugin.BasicPlugin;
import net.brord.plugins.defaultplugin.commands.commandutils.CommandBase;
import net.brord.plugins.defaultplugin.interfaces.SqlCommand;

/**
 * @author Brord
 *
 */
public class CmdSql extends CommandBase implements SqlCommand {

	/**
	 * @param sender
	 * @param command
	 * @param args
	 * @param permission
	 */
	public CmdSql(String command, BasicPlugin plugin) {
		super(command, plugin);
	}

	/* (non-Javadoc)
	 * @see net.castegaming.plugins.commands.CommandBase#handle()
	 */
	@Override
	public String handle() {
		return "";
	}

}
