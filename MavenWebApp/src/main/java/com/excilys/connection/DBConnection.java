package com.excilys.connection;

import java.sql.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.helper.Constant;

public class DBConnection {

    private static DBConnection connection = null;    
    private static Logger log = LoggerFactory.getLogger(DBConnection.class);
    
    /**
     * Private constructor
     */
    private DBConnection() {}

    /**
     *
     * Access point for the singleton instance
     * @return instance of DBConnection class
     */
    public static synchronized DBConnection getInstance() {
        if (connection == null) {
        	connection = new DBConnection();
        }
        return connection;
    }
    
    public Connection getSQLConnection() throws SQLException {
		return DriverManager.getConnection(Constant.URL+Constant.DB_NAME,Constant.USERNAME,Constant.PASSWORD);
	}
    
    public Connection getH2Connection() throws SQLException {
		return DriverManager.getConnection(Constant.H2_URL,Constant.H2_USERNAME,Constant.H2_PASSWORD);
	}
    
	public static void closeSqlResources(PreparedStatement preparedStatement, ResultSet result) {
		try {
			result.close();
		} catch (Exception e) {
			log.debug(e.getMessage());
		}
		
		try {
			preparedStatement.close();
		} catch (Exception e) {
			log.debug(e.getMessage());
		}
	}

	public static void closeSqlResources(PreparedStatement preparedStatement) {
		try {
			preparedStatement.close();
		} catch (Exception e) {
			log.debug(e.getMessage());
		}
	}
}
