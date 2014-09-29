/**
 * 
 */
package net.brord.plugins.defaultplugin.datastore.database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * @author Brord
 * 
 */
public abstract class DataBase {

	protected Connection connection;

	/**
	 * 
	 */
	public DataBase() {

	}

	/**
	 * Opens a connection with the database
	 * 
	 * @return Any error messages
	 */
	public abstract String openConnection();

	/**
	 * Makes a query to get records
	 * 
	 * @param query
	 *            the query
	 * @return the resultset or null if it failed
	 */
	public ResultSet querySQL(String query) {
		if (!setupConnection())
			return null;
		Statement s = null;

		try {
			s = connection.createStatement();
		} catch (SQLException e) {
			// e.printStackTrace();
			return null;
		}

		ResultSet ret = null;

		try {
			ret = s.executeQuery(query);
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		return ret;
	}

	/**
	 * Opens a connection if it was not yet opened
	 * 
	 * @return true if it was opened, or already open
	 */
	private boolean setupConnection() {
		if (!checkConnection()) {
			return openConnection() == null;
		}
		return true;
	}

	/**
	 * @return
	 */
	public Connection getConnection() {
		return connection;
	}

	/**
	 * Checks if the connection is closed. Does not check if its valid.
	 * 
	 * @return
	 */
	public boolean checkConnection() {
		try {
			return !connection.isClosed();
		} catch (SQLException e) {
			return false;
		}
	}

	public boolean updateSQL(String update) {
		if (!setupConnection())
			return false;
		Statement s = null;

		try {
			s = connection.createStatement();
			s.executeUpdate(update);
		} catch (SQLException e1) {
			e1.printStackTrace();
			return false;
		}

		closeConnection();
		return true;

	}

	/**
	 * Closes the connection if it was open
	 * 
	 * @return null if it was closed successfully, or or was closed. Otherwise
	 *         the error message
	 * 
	 */
	public String closeConnection() {
		if (!checkConnection())
			return null;
		try {
			connection.close();
			return null;
		} catch (SQLException e) {
			return e.getMessage();
		}
	}

}
