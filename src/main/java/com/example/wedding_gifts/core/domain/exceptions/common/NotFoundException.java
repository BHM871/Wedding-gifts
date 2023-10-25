package com.example.wedding_gifts.core.domain.exceptions.common;

public abstract class NotFoundException extends MyException {

    private static int statusCode = 422;
    private static String error = "Unprocessable Entity";
    private static String message = "Not Found";

    public NotFoundException(String message, String exception, Throwable cause) {
        super(cause, statusCode, error, exception, message);
    }

    public NotFoundException(String message, String exception) {
        super(statusCode, error, exception, message);
    }

    public NotFoundException(String exception) {
        super(statusCode, error, exception, message);
    }

    public NotFoundException() {
        super(statusCode, error, "NotFoundException.class", message);
    }

}
