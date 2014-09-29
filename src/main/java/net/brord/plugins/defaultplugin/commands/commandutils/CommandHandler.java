package net.brord.plugins.defaultplugin.commands.commandutils;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.HashMap;

import net.brord.plugins.defaultplugin.BasicPlugin;
import net.brord.plugins.defaultplugin.commands.commandbase.CmdHelp;
import net.brord.plugins.defaultplugin.commands.commandbase.CmdInfo;
import net.brord.plugins.defaultplugin.commands.commandbase.CmdReload;
import net.brord.plugins.defaultplugin.commands.exceptions.NoConsoleException;
import net.brord.plugins.defaultplugin.commands.exceptions.NoPermissionsException;
import net.brord.plugins.defaultplugin.commands.exceptions.NotIngameException;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public class CommandHandler {
	private HashMap<String, CommandBase> cmds = new HashMap<String, CommandBase>();

	private BasicPlugin plugin;
	
	public CommandHandler(BasicPlugin plugin) {
		this.plugin = plugin;
		
		addCmd("info", CmdInfo.class);
		addCmd("reload", CmdReload.class);
		addCmd("help", CmdHelp.class);
	}
	
	/**
	 * 
	 * @param name The name of the command
	 * @param clazz The class for the command
	 */
	public void addCmd(String name, Class<? extends CommandBase> clazz){
		try {
			cmds.put(name.toLowerCase(), clazz.getConstructor(String.class, BasicPlugin.class).newInstance(name.toLowerCase(), plugin));
		} catch (InstantiationException | IllegalAccessException
				| IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException | SecurityException e) {
			plugin.log("Oeps! Loading command " + name + " went wrong!");
			plugin.log(e.getMessage());
		}
	}
	
	/**
	 * @return 
	 * 
	 */
	public CommandBase getCmd(String name) {
		return cmds.get(name.toLowerCase());
	}
	
	/**
	 * @return 
	 * 
	 */
	public <T extends CommandBase> T getCmd(Class<T> clazz) {
		for (CommandBase c : cmds.values()){
			if (c.getClass().equals(clazz)) return (T) c;
		}
		return null;
	}
	
	/**
	 * Removes a command form the list
	 * @param name The name of the command
	 * @return The removed command class, or <code>null</code>
	 */
	public CommandBase removeCmd(String name){
		return cmds.remove(name.toLowerCase());
	}
	
	public boolean handle(CommandSender sender, Command command, String[] args){
		CommandBase cmd = null;
		String handled = null;
		String label = command.getName();
		
		if (label.toLowerCase().equals(plugin.getName().toLowerCase())){
			//main command
			if (args.length == 0){
				//no extra params = info message
				label = "info";
			} else {
				//else label is first param
				label = args[0];
				if (args.length > 1){
					args = Arrays.copyOfRange(args, 1, args.length);
				} else {
					args = new String[]{};
				}
			}
		}
		
		try {
			cmd = cmds.get(label.toLowerCase());
			if (cmd == null) cmd = getCmd(CmdHelp.class);
			
			handled = cmd.run(sender, args);
		} catch (Throwable e) {
			handleThrowable(e, sender);
		}
		
		if (handled == null){
			plugin.msg(sender, getHelp(command, label));
		} else if (handled != ""){
			plugin.msg(sender, handled);
		}
		return true;
	}
	
	/**
	 * Handles a throwable caused by someone runnign a command
	 * @param e
	 * @param sender
	 */
	private void handleThrowable(Throwable e, CommandSender sender) {
		if (e instanceof InvocationTargetException){
			e = e.getCause();
		}
		
		if (e instanceof NoConsoleException){
			plugin.msg(sender, plugin.getMessage("mustbeconsole"));
		} else if (e instanceof NotIngameException){
			plugin.msg(sender, plugin.getMessage("mustbeingame"));
		} else if (e instanceof NoPermissionsException){
			plugin.msg(sender, plugin.getMessage("nopermission").replace("<perm>", e.getCause().getMessage()));
		} else {
			e.printStackTrace();
		}
	}

	private String convertLabel(String oldLabel, String[] args){
		String label = oldLabel;
		if (oldLabel.toLowerCase().equals(plugin.getName().toLowerCase())){
			//main command
			if (args.length == 0){
				//no extra params = info message
				label = "info";
			} else {
				//else label is first param
				label = args[0];
				if (args.length > 1){
					args = Arrays.copyOfRange(args, 1, args.length);
				} else {
					args = new String[]{};
				}
			}
		}
		return label;
	}
	
	/**
	 * @param command2
	 * @param label
	 * @return
	 */
	private String getHelp(Command command, String label) {
		Command cmd = Bukkit.getServer().getPluginCommand(label);
		if (cmd != null) return cmd.getUsage();
		return command.getUsage();
	}
	
	/**
	 * @return
	 */
	public HashMap<String, CommandBase> getCommands() {
		return cmds;
	}
}
