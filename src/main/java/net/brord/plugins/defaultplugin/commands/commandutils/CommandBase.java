package net.brord.plugins.defaultplugin.commands.commandutils;

import net.brord.plugins.defaultplugin.BasicPlugin;
import net.brord.plugins.defaultplugin.commands.exceptions.NoConsoleException;
import net.brord.plugins.defaultplugin.commands.exceptions.NoPermissionsException;
import net.brord.plugins.defaultplugin.commands.exceptions.NotIngameException;
import net.brord.plugins.defaultplugin.interfaces.ConsoleCommand;
import net.brord.plugins.defaultplugin.interfaces.IngameCommand;

import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

public abstract class CommandBase {

	public CommandSender sender;
	public String cmd;
	public String[] args;
	public String permission;

	private BasicPlugin plugin;
	
	public CommandBase(String name, BasicPlugin plugin){
		this.cmd = name;
		this.plugin = plugin;
		this.permission = plugin.getName().toLowerCase() + "." + name;
	}
	
	/**
	 * This method gets run on command usage<br/>
	 * Return <code>null</code> for failed<br/>
	 * Return empty for good<br/>
	 * Return a message to be send to the user
	 * @param sender
	 * @param args
	 * @return message
	 * @throws Exception
	 */
	public String run(CommandSender sender, String[] args) throws Exception{
		this.sender = sender;
		this.args = args;
		
		checkRun();
		return handle();
	}
	
	public boolean checkRun() throws Exception{
		if (!hasPermission()) {
			throw new NoPermissionsException(permission);
		}
		
		if (this instanceof IngameCommand){
			if (!(sender instanceof Player)) throw new NotIngameException();
		}
		if (this instanceof ConsoleCommand){
			if (!(sender instanceof ConsoleCommandSender)) throw new NoConsoleException();
		}
		
		return true;
	}
	
	public abstract String handle();
	
	public BasicPlugin getPlugin(){
		return plugin;
	}
	
	/**
	 * Message this command sender
	 * @param message
	 */
	public void msg(String message){
		getPlugin().msg(sender, message);
	}
	
	/**
	 * Requests a message from the messages config
	 * @param message
	 * @return
	 */
	public String getMsg(String message){
		return getPlugin().getMessage(message);
	}
	
	/**
	 * 
	 * @param start from here till the end
	 * @return the string or <code>null</code>
	 */
	public String getString(int start){
		return getString(start, args.length);
	}
	
	/**
	 * 
	 * @param start included
	 * @param end excluded
	 * @return the string or <code>null</code>
	 */
	public String getString(int start, int end){
		if (start >= args.length) return null;
		if (end > args.length) end = args.length;
		
		
		String reason = "";
		for (int i=start; i<end; i++){
			reason += args[i] + " ";
		}
		
		return reason.substring(0, reason.length()-1);
	}
	
	/**
	 * Returns the player which used this command
	 * @return the {@link Player}
	 */
	public Player getPlayer(){
		if (!(sender instanceof Player))return null;
		return (Player) sender;
	}
	
	/**
	 * Gets the name of this command sender
	 * @return
	 */
	public String getName(){
		return sender.getName();
	}
	
	public boolean hasPermission(){
		return hasPermission(permission);
	}
	
	public boolean hasPermission(String string){
		return permission != null && permission != "" && sender.hasPermission(permission);
	}
	
	/**
	 * 
	 * @param perm The last part of the permission. <br/>
	 * Base command permission will be added infront
	 * @throws NoPermissionsException
	 */
	public void checkPerm(String perm) throws NoPermissionsException{
		if (hasPermission(permission + "." + perm)) return;
		throw new NoPermissionsException("User is missing permission " + perm);
	}
}
