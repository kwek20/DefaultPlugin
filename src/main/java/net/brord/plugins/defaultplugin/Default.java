/**
 * 
 */
package net.brord.plugins.defaultplugin;

import net.brord.plugins.defaultplugin.commands.CmdExample;
import net.brord.plugins.defaultplugin.config.BasicConfig;
import net.brord.plugins.defaultplugin.config.DefaultConfig;
import net.brord.plugins.defaultplugin.listeners.DefaultListener;

import org.bukkit.plugin.java.JavaPlugin;

/**
 * @author Brord
 * 
 */
public class Default extends SqlPlugin {

	private static Default plugin;
	
	@Override
	public void onEnable(){
		super.onEnable();
		plugin = this;
	}
	
	@Override
	public boolean load() {
		checkConfig("config.yml");
		mainconfig = new DefaultConfig(getConfig());
		return super.load();
	}
	
	/**
	  * @see net.brord.plugins.defaultplugin.BasicPlugin#onReload()
	  */
	@Override
	public void onReload() {
		
	}

	/**
	  * @see net.brord.plugins.ghastlegionutil.GHPlugin#getListeners()
	  */
	@Override
	public void loadListeners() {
		addListener(new DefaultListener(this));
	}
	
	/**
	  * @see net.brord.plugins.ghastlegionutil.GHPlugin#loadCommands()
	  */
	@Override
	public void loadCommands() {
		addCommand("example", CmdExample.class);
	}
	
	/**
	  * @see net.brord.plugins.defaultplugin.BasicPlugin#loadConfigs()
	  */
	@Override
	public void loadConfigs() {
		addConfig(new BasicConfig("exampleconfig"));
	}
	
	/**
	  * @see net.brord.plugins.defaultplugin.BasicPlugin#loadManagers()
	  */
	@Override
	public boolean loadManagers() {
		return true;
	}
	
	/**
	 * Casts the main config to the implementation
	 * @return
	 */
	public DefaultConfig getMainConfig(){
		return (DefaultConfig) mainconfig;
	}
	
	
	/**
	 * Sends the main class instance
	 * @return The {@link JavaPlugin} instance of this plugin
	 */
	public static Default getInstance(){
		return plugin;
	}
}
