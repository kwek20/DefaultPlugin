package net.brord.plugins.defaultplugin.datastore;

import net.brord.plugins.defaultplugin.commands.commandutils.CommandBase;
import net.brord.plugins.defaultplugin.util.ReflectionUtils;
import net.minecraft.util.io.netty.channel.MessageSizeEstimator.Handle;

/**
 * This {@link ExtendedCommand} is a extended {@link SubCommand}<br/>
 * Its main feature is to link a command to another command<br/>
 * To do so, it links the command and relays it to the new {@link Handle} command
 * Project GhastLegionUtil<br/>
 * Class net.brord.plugins.ghastlegionutil.datastore.ExtendedCommand.java<br/>
 * @author Brord
 * @since 18 sep. 2014, 01:18:43
 */
public class ExtendedCommand extends SubCommand {

	private CommandBase parent;
	
	/**
	 * @param name
	 * @param clazz
	 */
	public ExtendedCommand(String name, CommandBase clazz, CommandBase parent) {
		super(name, "run", clazz);
		
		this.parent = parent;
	}
	
	/**
	  * @see net.brord.plugins.ghastlegionutil.datastore.SubCommand#run()
	  */
	@Override
	public Object run() {
		return ReflectionUtils.runMethod(getClazz(), getMethod(), parent.sender, parent.args);
	}

}
