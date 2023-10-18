package com.example.wedding_gifts.core.domain.exceptions.commun;

public abstract class NotNullableException extends MyException {

    private static int statusCode = 406;
    private static String message = "Value is null";

    public NotNullableException(String message, String exception, Throwable cause) {
        super(cause, statusCode, exception, message);
    }

    public NotNullableException(String message, String exception) {
        super(statusCode, exception, message);
    }

    public NotNullableException(String exception) {
        super(statusCode, exception, message);
    }

    public NotNullableException() {
        super(statusCode, "NotNullableException.class", message);
    }
    
}
