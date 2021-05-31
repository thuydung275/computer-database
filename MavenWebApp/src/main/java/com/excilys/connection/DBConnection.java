package com.excilys.connection;

import org.apache.log4j.Logger;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

public class DBConnection {

    private static Logger log = Logger.getLogger(DBConnection.class);
    private static HikariConfig config;
    private static HikariDataSource dataSource;
    private static final String URL_PROPERTIES = "/db.properties";

    private DBConnection() {
        this.dataSource = new HikariDataSource(config);
    }

    /**
     * Holder chargée uniquement une fois lors du premier appel de "getInstance()".
     * sur la classe Singleton
     */
    private static class SingletonHolder {

        static {
            DBConnection.config = new HikariConfig(URL_PROPERTIES);
        }

        // Instance unique non préinitialisée.
        private static final DBConnection INSTANCE = new DBConnection();
    }

    /**
     * Point d'accès pour l'instance unique du singleton.
     *
     * @return instance of DBConnection class
     */
    public static DBConnection getInstance() {
        return SingletonHolder.INSTANCE;
    }

    public HikariDataSource getDataSource() {
        return dataSource;
    }

}
