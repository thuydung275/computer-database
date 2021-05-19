package com.excilys.helper;

public class Constant {
	
//	public static final String URL = "jdbc:mysql://localhost:3306/";
//	public static final String DB_NAME = "computer-database-db";
//	public static final String DRIVER = "com.mysql.cj.jdbc.Driver";
//	public static final String USERNAME = "admincdb";
//	public static final String PASSWORD = "qwerty1234";
	
	public static final String DBCONFIGFILE = "db.properties";
	public static final String URL = "jdbcUrl";
	public static final String DRIVER = "driverClassName";
	public static final String USERNAME = "username";
	public static final String PASSWORD = "password";
	
	//connection error
	public static int ER_CONNECTION = 0;
	
	// generals errors
    public static int ER_NOT_FOUND = 1;
    public static int ER_ALREADY_EXIST = 2;
    public static int ER_CREATE = 3;
    public static int ER_EDIT = 4;
    public static int ER_DELETE = 5;
    
    public static String TEXT_ER_NOT_FOUND = " does not exist in our database";
    
    
    // error computers
    public static int ER_DISCONTINUED_NULL_INTRODUCED_NOT_NULL = 111;
    public static int ER_INTRODUCED_BIGGER_DISCONTINUED = 112;

}
