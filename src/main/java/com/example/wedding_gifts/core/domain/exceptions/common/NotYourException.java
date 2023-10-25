package com.example.wedding_gifts.core.domain.exceptions.common;

public abstract class NotYourException extends MyException {

    private static int statusCode = 401;
    private static String error = "Unauthorizated";
    private static String message = "Objec is not your";

    public NotYourException(String message, String exception, Throwable cause) {
        super(cause, statusCode, error, exception, message);
    }

    public NotYourException(String message, String exception) {
        super(statusCode, error, exception, message);
    }

    public NotYourException(String exception) {
        super(statusCode, error, exception, message);
    }

    public NotYourException() {
        super(statusCode, error, "NotYourException.class", message);
    }
    
}
