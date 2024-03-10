package com.example.wedding_gifts_api.core.domain.exceptions.common;

public abstract class ForbiddenException extends MyException {

    private static int statusCode = 403;
    private static String error = "Forbidden";
    private static String message = "Unauthorizated";

    public ForbiddenException(String message, String exception, Throwable cause) {
        super(cause, statusCode, error, exception, message);
    }

    public ForbiddenException(String message, String exception) {
        super(statusCode, error, exception, message);
    }

    public ForbiddenException(String exception) {
        super(statusCode, error, exception, message);
    }

    public ForbiddenException() {
        super(statusCode, error, "ForbiddenException.class", message);
    }
    
}
