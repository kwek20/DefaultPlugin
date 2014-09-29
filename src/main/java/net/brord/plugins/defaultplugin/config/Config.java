/**
 * 
 */
package net.brord.plugins.defaultplugin.config;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * @author Brord
 * 
 */
public final class Config {
	
	private File folder;

	/**
	 * 
	 */
	public Config(File folder) {
		this.folder = folder;
	}

	public YamlConfiguration getConfig(String name) {
		if (!name.endsWith(".yml"))
			name += ".yml";
		return YamlConfiguration.loadConfiguration(new File(getDir() + name));

	}

	public boolean containsConfig(String name) {
		if (!name.endsWith(".yml"))
			name += ".yml";
		return new File(getDir() + name).exists();
	}

	public boolean saveConfig(YamlConfiguration config, String name) {
		if (!name.endsWith(".yml"))
			name += ".yml";
		try {
			config.save(getDir() + name);
			return true;
		} catch (IOException e) {
			return false;
		}
	}
	
	/**
	 * 
	 * @param name
	 * @return
	 */
	public boolean createConfig(String name){
		if (!name.endsWith(".yml"))
			name += ".yml";
		try {
			return new File(getDir() + name).createNewFile();
		} catch (IOException e) {
			return false;
		}
	}

	private String getDir() {
		return folder + File.separator;
	}
}
