package com.example.wedding_gifts.core.domain.exceptions.common;

public abstract class NotNullableException extends MyException {

    private static int statusCode = 406;
    private static String error = "Not Acceptable";
    private static String message = "Value is null";

    public NotNullableException(String message, String exception, Throwable cause) {
        super(cause, statusCode, error, exception, message);
    }

    public NotNullableException(String message, String exception) {
        super(statusCode, error, exception, message);
    }

    public NotNullableException(String exception) {
        super(statusCode, error, exception, message);
    }

    public NotNullableException() {
        super(statusCode, error, "NotNullableException.class", message);
    }
    
}
