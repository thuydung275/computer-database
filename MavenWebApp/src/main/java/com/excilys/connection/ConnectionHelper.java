package com.excilys.connection;

import java.sql.*;

import org.apache.log4j.Logger;

import com.excilys.App;

public class ConnectionHelper {
	
	private static Logger log = Logger.getLogger(ConnectionHelper.class);
    public final static String className = ConnectionHelper.class.getName();

    public static void closeSqlResources(Connection connection, PreparedStatement preparedStatement, ResultSet result) {
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
        try {
            connection.close();
        } catch (Exception e) {
            log.debug(e.getMessage());
        }
    }

}
