package com.example.wedding_gifts.core.domain.exceptions.common;

public abstract class NotYourException extends MyException {

    private static int statusCode = 403;
    private static String message = "Object is not your";

    public NotYourException(String message, String exception, Throwable cause) {
        super(cause, statusCode, exception, message);
    }

    public NotYourException(String message, String exception) {
        super(statusCode, exception, message);
    }

    public NotYourException(String exception) {
        super(statusCode, exception, message);
    }

    public NotYourException() {
        super(statusCode, "NotYourException.class", message);
    }
    
}
