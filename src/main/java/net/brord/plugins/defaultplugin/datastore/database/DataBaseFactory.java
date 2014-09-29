/**
 * 
 */
package net.brord.plugins.defaultplugin.datastore.database;

import org.bukkit.configuration.ConfigurationSection;

/**
 * @author Brord
 * 
 */
public class DataBaseFactory {
	private static DataBaseFactory factory;

	private DataBaseFactory() {
	}

	public DataBase getDataBase(ConfigurationSection s) {
		if (s == null)
			return null;
		String type = s.getString("type");
		if (type.equalsIgnoreCase("mysql")) {
			// plugin, hostname, port, database, username, password
			return new MySQL(
					s.getString("hostname", "localhost"), 
					s.getString("port", "3306"), 
					s.getString("database", "minecraft"),
					s.getString("username", "bukkit"), 
					s.getString("password",""));
		} else if (type.equalsIgnoreCase("sqlite")) {
			// plugin, location
			return new SQLite(s.getString("location", "sqlite.db"));
		} else {
			return null;
		}
	}

	public static DataBaseFactory get() {
		if (factory == null)
			factory = new DataBaseFactory();
		return factory;
	}
}
