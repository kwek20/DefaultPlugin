package net.brord.plugins.defaultplugin.commands;

import net.brord.plugins.defaultplugin.BasicPlugin;
import net.brord.plugins.defaultplugin.commands.commandutils.CommandBase;
import net.brord.plugins.defaultplugin.interfaces.IngameCommand;

/**
 * Project Default<br/>
 * Class net.brord.plugins.defaultplugin.commands.CmdExample.java<br/>
 * @author Brord
 * @since 23 jun. 2014, 14:23:17
 */
public class CmdExample extends CommandBase implements IngameCommand {

	/**
	 * @param sender
	 * @param command
	 * @param args
	 * @param plugin
	 */
	public CmdExample(String command, BasicPlugin plugin){
		super(command, plugin);
	}

	/**
	 * @see net.brord.plugins.defaultplugin.commands.commandutils.CommandBase#handle()
	 */
	@Override
	public String handle() {
		return "";
	}

}
