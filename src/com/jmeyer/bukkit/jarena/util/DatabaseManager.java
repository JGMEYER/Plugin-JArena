package com.jmeyer.bukkit.jarena.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.jmeyer.bukkit.jarena.JArenaPlugin;

/**
 * Handles calls to SQLite Database.
 * @author JMEYER
 **/
public class DatabaseManager {
	
	public static final Logger LOG = Logger.getLogger("Minecraft");
	public static final String ZONE_DB_DIRECTORY = "jdbc:sqlite:" + JArenaPlugin.ZONE_DIRECTORY;
	
	
	public static ResultSet getResultSet(String dbPath, String tableFrom) {
		return getResultSet(dbPath, tableFrom, null, 0);
	}
	
	public static ResultSet getResultSet(String dbPath, String tableFrom, String condition) {
		return getResultSet(dbPath, tableFrom, condition, 0);
	}
	
	public static ResultSet getResultSet(String dbPath, String tableFrom, String condition, int limit) {
		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;
		String query = null;
		try {
			Class.forName("org.sqlite.JDBC");
			conn = DriverManager.getConnection(dbPath);
			st = conn.createStatement();
			
			query = "SELECT * FROM " + tableFrom;
			
			if (condition != null)
				query += " WHERE " + condition;
			
			if (limit > 0) 
				query += " LIMIT " + limit;
			
			query += ";";
			
			rs = st.executeQuery(query);
			
			return rs;
		} catch (SQLException e) {
			LOG.log(Level.SEVERE, "[JArena]: Table Read Exception (getResult) \n" + query, e);
			return null;
		} catch (ClassNotFoundException e) {
			LOG.log(Level.SEVERE, "[JArena]: Error loading org.sqlite.JDBC");
			return null;
		} finally {
			try {
				if (conn != null)
					conn.close();
				if (st != null)
					st.close();
				if (rs != null)
					rs.close();
			} catch (SQLException e) {
				LOG.log(Level.SEVERE, "[JArena]: Could not read the table (on close) (getResult) \n" + query);
			}
		}
	}
	
	public static void runUpdate(String dbPath, String update) {
		Connection conn = null;
		Statement st = null;
		try {
			Class.forName("org.sqlite.JDBC");
			conn = DriverManager.getConnection(dbPath);
			st = conn.createStatement();
			
			st.executeUpdate(update);
		} catch (SQLException e) {
			LOG.log(Level.SEVERE, "[JArena]: Run Update Exception \n" + update, e);
		} catch (ClassNotFoundException e) {
			LOG.log(Level.SEVERE, "[JArena]: Error loading org.sqlite.JDBC");
		} finally {
			try {
				if (conn != null)
					conn.close();
				if (st != null)
					st.close();
			} catch (SQLException e) {
				LOG.log(Level.SEVERE, "[JArena]: Could not run update \n" + update);
			}
		}
	}
	
}
