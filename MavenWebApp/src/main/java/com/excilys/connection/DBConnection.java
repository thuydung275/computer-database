package com.excilys.connection;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;
import org.apache.log4j.Logger;

import com.excilys.validator.CustomException;

public class DBConnection {

    private static Logger log = Logger.getLogger(DBConnection.class);
    public String className = DBConnection.class.getName();
    private DataSource dataSource = null;

    /**
     * Constructeur privé.
     */
    private DBConnection() {
        BasicDataSource ds = new BasicDataSource();
        ds.setUrl(SingletonHolder.url);
        ds.setDriverClassName(SingletonHolder.driver);
        ds.setUsername(SingletonHolder.username);
        ds.setPassword(SingletonHolder.password);
        dataSource = ds;
    }

    public DataSource getDataSource() {
        return dataSource;
    }

    /**
     * Holder chargée uniquement une fois lors du premier appel de "getInstance()".
     * sur la classe Singleton
     */
    private static class SingletonHolder {

        private static String password;
        private static String username;
        private static String driver;
        private static String url;

        static {
            Properties properties = new Properties();
            ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
            InputStream configFile = classLoader.getResourceAsStream(ConnectionConstant.DBCONFIGFILE);
            if (configFile == null) {
                throw new CustomException("Config file empty !", 0);
            }
            try {
                properties.load(configFile);
                SingletonHolder.url = properties.getProperty(ConnectionConstant.URL);
                SingletonHolder.driver = properties.getProperty(ConnectionConstant.DRIVER);
                SingletonHolder.username = properties.getProperty(ConnectionConstant.USERNAME);
                SingletonHolder.password = properties.getProperty(ConnectionConstant.PASSWORD);
            } catch (IOException ex) {
                log.error(ex);
                throw new CustomException(ex.getMessage(), 0);
            }
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
}
