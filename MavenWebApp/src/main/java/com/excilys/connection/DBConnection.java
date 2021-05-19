package com.excilys.connection;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.sql.DataSource;
import org.apache.commons.dbcp.BasicDataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.helper.Constant;
import com.excilys.helper.CustomException;

public class DBConnection {

	private static Logger logger = LoggerFactory.getLogger(DBConnection.class);
    public final static String className = DBConnection.class.getName();
    private DataSource dataSource = null;

	/**
     * Constructeur privé
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
    
    /** Holder chargée uniquement une fois lors du premier appel de "getInstance()" sur la classe Singleton */
    private static class SingletonHolder {
    	
    	private static String password;
    	private static String username;
		private static String driver;
		private static String url;

		static {
            Properties properties = new Properties();
    		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
    		InputStream configFile = classLoader.getResourceAsStream(Constant.DBCONFIGFILE);
    		if (configFile == null) {
    			throw new CustomException("Config file empty !",0);
    		}
    		try {
    			properties.load(configFile);
    			SingletonHolder.url = properties.getProperty(Constant.URL);
    			SingletonHolder.driver = properties.getProperty(Constant.DRIVER);
    			SingletonHolder.username = properties.getProperty(Constant.USERNAME);
    			SingletonHolder.password = properties.getProperty(Constant.PASSWORD);
    		} catch (IOException ex) {
    			throw new CustomException(ex.getMessage(), 0);
    		} 
    	}
    	
        /** Instance unique non préinitialisée */
        private final static DBConnection instance = new DBConnection();
    }
    
    /** Point d'accès pour l'instance unique du singleton
     * @return instance of DBConnection class
     */
    public static DBConnection getInstance() {
		return SingletonHolder.instance;
    }
}
