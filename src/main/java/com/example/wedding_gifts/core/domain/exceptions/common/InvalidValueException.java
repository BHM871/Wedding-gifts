package com.example.wedding_gifts.core.domain.exceptions.common;

public abstract class InvalidValueException extends MyException {

    private static int statusCode = 422;
    private static String error = "Unprocessable Entity";
    private static String message = "Invalid Value";

    public InvalidValueException(String message, String exception, Throwable cause) {
        super(cause, statusCode, error, exception, message);
    }

    public InvalidValueException(String message, String exception) {
        super(statusCode, error, exception, message);
    }

    public InvalidValueException(String exception) {
        super(statusCode, error, exception, message);
    }

    public InvalidValueException() {
        super(statusCode, error, "InvalidValueException.class", message);
    }

}
