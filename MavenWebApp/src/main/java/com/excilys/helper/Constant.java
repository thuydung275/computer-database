package com.excilys.helper;

public class Constant {

    public static final String DBCONFIGFILE = "db.properties";
    public static final String URL = "jdbcUrl";
    public static final String DRIVER = "driverClassName";
    public static final String USERNAME = "username";
    public static final String PASSWORD = "password";

    // connection error
    public static final int ER_CONNECTION = 0;

    // generals errors
    public static final int ER_NOT_FOUND = 1;
    public static final int ER_ALREADY_EXIST = 2;
    public static final int ER_CREATE = 3;
    public static final int ER_EDIT = 4;
    public static final int ER_DELETE = 5;

    public static final String TEXT_ER_NOT_FOUND = " does not exist in our database";

    // error computers
    public static final int ER_DISCONTINUED_NULL_INTRODUCED_NOT_NULL = 111;
    public static final int ER_INTRODUCED_BIGGER_DISCONTINUED = 112;

}
