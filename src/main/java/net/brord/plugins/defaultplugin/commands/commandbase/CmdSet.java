/**
 * 
 */
package net.brord.plugins.defaultplugin.commands.commandbase;

import java.lang.reflect.Method;

import net.brord.plugins.defaultplugin.BasicPlugin;
import net.brord.plugins.defaultplugin.annotations.Precondition;
import net.brord.plugins.defaultplugin.commands.commandutils.CommandBase;
import net.brord.plugins.defaultplugin.commands.exceptions.CheckException;
import net.brord.plugins.defaultplugin.datastore.SubCommand;
import net.brord.plugins.defaultplugin.managers.Manager;
import net.brord.plugins.defaultplugin.util.Parse;
import net.brord.plugins.defaultplugin.util.ReflectionUtils;

/**
 * @author Brord
 *
 */
public abstract class CmdSet extends CommandBase {
	
	private Manager<SubCommand> SETS = new Manager<SubCommand>(SubCommand.class);
	
	/** 
	 * Adds a {@link SubCommand} to the list.<br/>
	 * The {@link SubCommand} name is equal to the method name
	 * @param cmd The {@link SubCommand} to add
	 * @return <code>true</code> if the method existed, otherwise <code>false</code>
	 */
	public boolean addCommand(SubCommand cmd){
		SETS.add(cmd);
		return true;
	}
	
	public boolean addCommand(String name){
		return addCommand(getSCM(name));
	}
	
	/**
	 * @param sender
	 * @param command
	 * @param args
	 * @param permission
	 */
	public CmdSet(String command, BasicPlugin plugin) {
		super(command, plugin);
	}
	
	protected SubCommand getSCM(String name){
		return new SubCommand(name, this);
	}
	
	/**
	  * @see net.brord.plugins.ghastlegionutil.commands.commandutils.CommandBase#checkRun()
	  */
	@Override
	public boolean checkRun() throws Exception {
		super.checkRun();
		if (args.length == 0) return true;
		
		SubCommand c = getCommand(args[0]);
			
		if (c != null){
			checkPerm(c.getName());
			checkMethod(c);
		}
		return true;
	}

	/** 
	 * @see net.castegaming.plugins.commands.commandutils.brord.rschallenge.commands.CommandBase#handle()
	 */
	@Override
	public String handle() {
		Object msg = null;
		SubCommand c = null;
		if (args.length > 0){
			c = getCommand(args[0]);
		} 
		
		if (c != null && (msg = c.run()) != null){
			if (msg instanceof String) return msg.toString();
		} else {
			return getPlugin().getMessage("setoptions").replaceAll("<options>", Parse.arrayToString(SETS.getAll().toArray()));
		}
		
		return "";
	}
	
	/**
	 * @param c
	 * @return 
	 */
	private void checkMethod(SubCommand c) throws CheckException{
		if (!c.getMethod().isAnnotationPresent(Precondition.class)) return;
	
		Precondition pre = c.getMethod().getAnnotation(Precondition.class);
		Method check = ReflectionUtils.getMethod(c.getClazz().getClass(), pre.value());
		
		Object ret = ReflectionUtils.runMethod(c.getClazz(), check);
		if (ret instanceof Boolean){
			if (!(Boolean)ret){
				throw new CheckException(cmd);
			}
		}
	}

	/**
	 * @param string
	 * @return
	 */
	private synchronized SubCommand getCommand(String cmd) {
		for (SubCommand s : SETS){
			if (s.getName().equals(cmd)) return s;
		}
		
		for (SubCommand s : SETS){
			if (s.getName().equalsIgnoreCase(cmd)) return s;
		}
		
		for (SubCommand s : SETS){
			for (String alias : s.getAliases()){
				if (alias.equals(cmd)) return s;
			}
		}
		
		for (SubCommand s : SETS){
			for (String alias : s.getAliases()){
				if (alias.equalsIgnoreCase(cmd)) return s;
			}
		}
		
		return null;
	}
}
