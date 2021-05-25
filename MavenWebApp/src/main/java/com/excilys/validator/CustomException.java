package com.excilys.validator;

public class CustomException extends RuntimeException {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

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

    private int code;

    /**
     *
     * @param code
     */
    public CustomException(int code) {
        super();
        this.code = code;
    }

    /**
     *
     * @param message
     * @param code
     */
    public CustomException(String message, int code) {
        super(message);
        this.code = code;
    }

    /**
     *
     * @param cause
     * @param code
     */
    public CustomException(Throwable cause, int code) {
        super(cause);
        this.code = code;
    }

    /**
     *
     * @param message
     * @param cause
     * @param code
     */
    public CustomException(String message, Throwable cause, int code) {
        super(message, cause);
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

}
