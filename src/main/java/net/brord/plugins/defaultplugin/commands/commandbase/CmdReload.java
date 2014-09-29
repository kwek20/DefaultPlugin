/**
 * 
 */
package net.brord.plugins.defaultplugin.commands.commandbase;

import net.brord.plugins.defaultplugin.BasicPlugin;
import net.brord.plugins.defaultplugin.commands.commandutils.CommandBase;

/**
 * @author Brord
 *
 */
public class CmdReload extends CommandBase {

	/**
	 * @param sender
	 * @param command
	 * @param args
	 * @param permission 
	 * @param permission
	 */
	public CmdReload(String command, BasicPlugin plugin) {
		super(command, plugin);
	}

	@Override
	public String handle() {
		getPlugin().reloadConfig();
		getPlugin().onReload();
		getPlugin().loadAll();
		return getPlugin().getMessage("reload");
	}
}
