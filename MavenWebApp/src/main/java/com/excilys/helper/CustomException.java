package com.excilys.helper;

public class CustomException extends RuntimeException {

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
