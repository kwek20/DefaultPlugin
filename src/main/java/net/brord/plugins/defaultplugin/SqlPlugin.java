/**
 * 
 */
package net.brord.plugins.defaultplugin;

import java.sql.ResultSet;

import net.brord.plugins.defaultplugin.config.SqlMainConfig;
import net.brord.plugins.defaultplugin.datastore.database.DataBase;

/**
 * @author Brord
 * 
 */
public abstract class SqlPlugin extends BasicPlugin {
	
	private DataBase sql;

	public SqlMainConfig getMainConfig(){
		return (SqlMainConfig) mainconfig;
	}
	
	@Override
	public boolean load() {
		saveDefaultConfig();
		loadConfig();

		if (loadSql()) {
			loadListeners();
			makeTables();
			return true;
		} else {
			log(getMessage("nosql"));
			getServer().getPluginManager().disablePlugin(this);
			return false;
		}
	}


	public void makeTables() {
		// table for players and their balance
		insertQuery("SET sql_notes = 0;");
		insertQuery("CREATE TABLE IF NOT EXISTS " + getMainConfig().getTableName()
				+ " (" + "name varchar(16) NOT NULL,"
				+ "balance float NOT NULL," + "renttime bigint NOT NULL,"
				+ "CONSTRAINT pk_name PRIMARY KEY (name)" + ")");
		log("Checked tables");
		insertQuery("SET sql_notes = 1;");
	}

	/**
	 * Returns true if it was successfull
	 * 
	 * @return
	 */
	public boolean loadSql() {
		sql = getMainConfig().getSql();
		if (sql == null) {
			logDebug("WARNING! Anything but MySQL and SQLite is not yet supported!");
			return false;
		}
		openConnection();
		return sql.checkConnection();
	}

	/**
	 * Executes a query if possible
	 * 
	 * @param sql
	 *            The query to execute
	 * @return The {@link ResultSet} or <code>null</code>
	 */
	public ResultSet query(String sql) {
		if (sql == null)
			return null;
		return this.sql.querySQL(sql);
	}

	/**
	 * Executes a insertquery if possible
	 * 
	 * @param sql
	 *            The query to insert
	 * @return true if it was successful
	 */
	public boolean insertQuery(String sql) {
		if (sql == null)
			return false;
		return this.sql.updateSQL(sql);
	}

	/**
	 * closes the sql connection
	 */
	public void closeConnection() {
		if (sql == null)
			return;
		String msg = sql.closeConnection();
		if (msg != null)
			logDebug(msg);
	}

	/**
	 * opens the sql connection
	 */
	public void openConnection() {
		if (sql == null) return;
		String msg = sql.openConnection();
		if (msg != null)
			logDebug(msg);
	}

	/**
	 * @return if it can connect to the database
	 */
	public boolean canConnectSql() {
		if (sql == null)
			return false;
		openConnection();
		return sql.checkConnection();
	}
}
