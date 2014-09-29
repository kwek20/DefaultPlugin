/**
 * 
 */
package net.brord.plugins.defaultplugin.datastore.database;

import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * @author Brord
 * 
 */
public class MySQL extends DataBase {
	private final String user;
	private final String database;
	private final String password;
	private final String port;
	private final String hostname;

	/**
	 * @param plugin
	 * @param user
	 * @param database
	 * @param password
	 * @param port
	 * @param hostname
	 */
	public MySQL(String user, String database, String password, String port,
			String hostname) {
		this.user = user;
		this.database = database;
		this.password = password;
		this.port = port;
		this.hostname = hostname;
	}

	@Override
	public String openConnection() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			connection = DriverManager.getConnection("jdbc:mysql://"
					+ this.hostname + ":" + this.port + "/" + this.database,
					this.user, this.password);
		} catch (SQLException e) {
			return "Could not connect to MySQL server! because: "
					+ e.getMessage();
		} catch (ClassNotFoundException e) {
			return "JDBC Driver not found!";
		}
		return null;
	}
}
