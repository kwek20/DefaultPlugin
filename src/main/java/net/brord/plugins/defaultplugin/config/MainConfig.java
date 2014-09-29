package net.brord.plugins.defaultplugin.config;

import java.util.Map;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;

/**
 * Project Default<br/>
 * Class net.brord.plugins.config.MainConfig.java<br/>
 * @author Brord
 * @since 21 jun. 2014, 01:24:11
 */
public abstract class MainConfig {
	protected FileConfiguration config;

	/**
	 * 
	 */
	public MainConfig(FileConfiguration config) {
		this.config = config;
	}
	
	
	/**
	 * @return
	 */
	public String getPrefix() {
		return ChatColor.translateAlternateColorCodes('&', config.getString("prefix", "-> &4brokenprefix <-"));
	}
	
	/**
	 * @return
	 */
	public boolean isDebugOn() {
		return config.getBoolean("debug");
	}
	
	/**
	 * @return the config
	 */
	public FileConfiguration getConfig() {
		return config;
	}
	
	/**
	 * @return
	 */
	public Map<String, Object> getListeners() {
		return config.getConfigurationSection("listeners").getValues(false);
	}
}
