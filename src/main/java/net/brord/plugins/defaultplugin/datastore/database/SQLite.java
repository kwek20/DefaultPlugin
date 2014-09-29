/**
 * 
 */
package net.brord.plugins.defaultplugin.datastore.database;

import java.io.File;
import java.io.IOException;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * @author Brord
 * 
 */
public class SQLite extends DataBase {
	private String dbLocation;

	/**
	 * Starts a new SQlite dataclass
	 * 
	 * @param dbLocation
	 *            the location to read from. .db will be appended if it was not
	 *            yet there
	 */
	public SQLite(String dbLocation) {
		if (!dbLocation.endsWith(".db"))
			dbLocation += ".db";
		this.dbLocation = dbLocation;
	}

	@Override
	public String openConnection() {
		File file = new File(dbLocation);
		if (!(file.exists())) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				return "Unable to create database!";
			}
		}
		try {
			Class.forName("org.sqlite.JDBC");
			connection = DriverManager.getConnection("jdbc:sqlite:"
					+ dbLocation);
		} catch (SQLException e) {
			return "Could not connect to SQLite server! because: "
					+ e.getMessage();
		} catch (ClassNotFoundException e) {
			return "JDBC Driver not found!";
		}
		return null;
	}
}
