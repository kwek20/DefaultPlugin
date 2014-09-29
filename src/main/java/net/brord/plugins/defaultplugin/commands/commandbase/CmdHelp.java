package net.brord.plugins.defaultplugin.commands.commandbase;

import net.brord.plugins.defaultplugin.BasicPlugin;
import net.brord.plugins.defaultplugin.commands.commandutils.CommandBase;
import net.brord.plugins.defaultplugin.util.Parse;


/**
 * Project Default<br/>
 * Class net.brord.plugins.defaultplugin.commands.commandbase.CmdHelp.java<br/>
 * @author Brord
 * @since 25 jun. 2014, 13:24:00
 */
public class CmdHelp extends CommandBase {

	/**
	 * @param sender
	 * @param command
	 * @param args
	 * @param plugin
	 */
	public CmdHelp(String command, BasicPlugin plugin){
		super(command, plugin);
	}

	/**
	 * @see net.brord.plugins.defaultplugin.commands.commandutils.CommandBase#handle()
	 */
	@Override
	public String handle() {
		return getMsg("setoptions").replaceAll("<options>", Parse.arrayToString(getPlugin().getCommandHandler().getCommands().keySet().toArray()));
	}
}
