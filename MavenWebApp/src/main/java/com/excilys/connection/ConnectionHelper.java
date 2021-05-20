package com.excilys.connection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.apache.log4j.Logger;

public class ConnectionHelper {

    private static Logger log = Logger.getLogger(ConnectionHelper.class);

    /**
     *
     * @param connection
     * @param preparedStatement
     * @param result
     */
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
