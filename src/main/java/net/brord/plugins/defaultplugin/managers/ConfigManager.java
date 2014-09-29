package net.brord.plugins.defaultplugin.managers;

import net.brord.plugins.defaultplugin.config.BasicConfig;

/**
 * Project Arena<br/>
 * Class net.brord.plugins.config.ConfigManager.java<br/>
 * @author Brord
 * @since 20 jun. 2014, 20:41:30
 */
public class ConfigManager extends Manager<BasicConfig>{
	
	//private Map<String, BasicConfig> configs;
	
	/**
	 * 
	 */
	public ConfigManager() {
		super(BasicConfig.class);
		//configs = new HashMap<String, BasicConfig>();
	}
	
	/**
	 * 
	 * @param name
	 * @param config
	 * @return The previous config or <code>null</code>
	 */
	public boolean addConfig(String name, BasicConfig config){
		return add(config);
		//return configs.put(name, config);
	}

	/**
	 * @param <T>
	 * @param <T>
	 * @param class1
	 */
	public <T> T getConfig(Class<T> clazz) {
		for (BasicConfig c : data){
			if (c.getClass().equals(clazz)) return (T) c;
		}
		
		return null;
	}
}
