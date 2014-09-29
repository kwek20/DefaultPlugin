/**
 * 
 */
package net.brord.plugins.defaultplugin.config;

import net.brord.plugins.defaultplugin.datastore.database.DataBase;
import net.brord.plugins.defaultplugin.datastore.database.DataBaseFactory;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;

/**
 * @author Brord
 *
 */
public abstract class SqlMainConfig extends MainConfig{

	public SqlMainConfig(FileConfiguration config) {
		super(config);
	}
	
	public String getTableName(){
		ConfigurationSection s = config.getConfigurationSection("sql");
		if (s == null) return "";
		return s.getString("tablename", "defaulttable");
	}

	/**
	 * @return
	 */
	public DataBase getSql() {
		DataBase d = DataBaseFactory.get().getDataBase(config.getConfigurationSection("sql"));
		return d;
	}
}
