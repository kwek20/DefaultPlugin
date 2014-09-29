/**
 * 
 */
package net.brord.plugins.defaultplugin.config;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.configuration.file.FileConfiguration;

/**
 * @author Brord
 *
 */
public class DefaultConfig extends SqlMainConfig{

	public DefaultConfig(FileConfiguration config) {
		super(config);
	}

	/**
	 * @return
	 */
	public Map<String, Integer> getCooldowns() {
		 Map<String, Integer> m = new HashMap<String, Integer>();
		for (String s : config.getConfigurationSection("cooldowns").getKeys(false)){
			m.put(s, config.getInt("cooldowns." + s));
		}
		return m;
	}
}
