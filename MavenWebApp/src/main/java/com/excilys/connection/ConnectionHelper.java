package com.excilys.connection;

import java.sql.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ConnectionHelper {
	
	private static Logger log = LoggerFactory.getLogger(ConnectionHelper.class);
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
