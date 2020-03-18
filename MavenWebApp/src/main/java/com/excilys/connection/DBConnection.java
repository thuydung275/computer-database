package com.excilys.connection;

import java.sql.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.helper.Constant;

public class DBConnection {

    private static Connection connection = null;    
    private static Logger log = LoggerFactory.getLogger(DBConnection.class);
    
    /**
     * Private constructor
     */
    private DBConnection() {}

    /**
     *
     * Access point for the singleton instance
     * @return MysqlConnect Database connection object
     */
    public static synchronized Connection getInstance() {
        if (connection == null) {
        	try {
            	// instantiation du driver JDBC
                Class.forName(Constant.DRIVER);
                connection = (Connection)DriverManager.getConnection(Constant.URL+Constant.DB_NAME,Constant.USERNAME,Constant.PASSWORD);
            }
            catch (Exception sqle) {
            	log.debug(sqle.getMessage());
            }
        }
        return connection;
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
