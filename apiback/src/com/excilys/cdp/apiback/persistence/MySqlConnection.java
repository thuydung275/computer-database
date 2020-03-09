package com.excilys.cdp.apiback.persistence;

import com.excilys.cdp.apiback.constant.Constant;
import java.sql.*;

public class MySqlConnection implements AutoCloseable {
	
	/**
	 * Single instance not pre-initialized
	 */
    private static MySqlConnection INSTANCE = null;
    private Connection connection;    
    private static int counter = 0;
    
    /**
     * Private constructor
     */
    private MySqlConnection() {
    	counter++; 	
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
        System.out.println("instance number : " + counter);
        return INSTANCE;
    }
    
    public Connection getConnection() {
    	return this.connection;
    }

	@Override
	public void close() throws Exception {
		connection.close();
	}
}
