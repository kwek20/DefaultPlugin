package net.brord.plugins.defaultplugin.config;

import java.io.File;

import net.brord.plugins.defaultplugin.interfaces.Managable;

import org.bukkit.configuration.file.YamlConfiguration;

/**
 * Project Arena<br/>
 * Class net.brord.plugins.config.BasicConfig.java<br/>
 * @author Brord
 * @since 20 jun. 2014, 20:30:55
 */
public class BasicConfig implements Managable{
	private String name;
	private YamlConfiguration config;
	
	/**
	 * @param string
	 * @param config
	 */
	public BasicConfig(String name) {
		this.name = name;
	}
	
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * @return the config
	 */
	public YamlConfiguration getConfig() {
		return config;
	}
	
	/**
	 * @param config
	 */
	public void setConfig(YamlConfiguration config) {
		this.config = config;
	}

	/**
	 * @see org.bukkit.configuration.file.YamlConfiguration#set(String, Object)
	 * @param name
	 * @param arg
	 */
	public void set(String name, Object arg){
		set(name, arg);
	}
	
	public void save(Config c){
		c.saveConfig(config, name);
	}

	/**
	 * @param msg
	 * @return
	 */
	public boolean contains(String path) {
		return config.contains(path);
	}

	/**
	 * @param msg
	 * @return
	 */
	public String getString(String msg) {
		return config.getString(msg);
	}

	/**
	  * @see net.brord.plugins.defaultplugin.interfaces.Managable#name()
	  */
	public String name() {
		return name;
	}
	
}
