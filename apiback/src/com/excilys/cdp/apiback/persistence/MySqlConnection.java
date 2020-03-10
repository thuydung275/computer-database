package com.excilys.cdp.apiback.persistence;

import java.sql.*;

import com.excilys.cdp.apiback.helper.Constant;

public class MySqlConnection {
	
	/**
	 * Single instance not pre-initialized
	 */
    private static MySqlConnection INSTANCE = null;
    private Connection connection;    
    
    /**
     * Private constructor
     */
    private MySqlConnection() {
        try {
        	// instantiation du driver JDBC
            Class.forName(Constant.DRIVER);
            connection = (Connection)DriverManager.getConnection(Constant.URL+Constant.DB_NAME,Constant.USERNAME,Constant.PASSWORD);
        }
        catch (Exception sqle) {
            sqle.printStackTrace();
        }
    }

    /**
     *
     * Access point for the singleton instance
     * @return MysqlConnect Database connection object
     */
    public static synchronized MySqlConnection getInstance() {
        if ( INSTANCE == null ) {
        	INSTANCE = new MySqlConnection();
        }
        return INSTANCE;
    }
    
    public Connection getConnection() {
    	return this.connection;
    }
}
