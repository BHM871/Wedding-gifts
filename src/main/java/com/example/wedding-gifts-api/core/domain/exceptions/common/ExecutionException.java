package com.example.wedding_gifts.core.domain.exceptions.common;

public abstract class ExecutionException extends MyException {

    private static int statusCode = 400;
    private static String error = "Bad Request";
    private static String message = "Execution Error";

    public ExecutionException(String message, String exception, Throwable cause) {
        super(cause, statusCode, error, exception, message);
    }

    public ExecutionException(String message, String exception) {
        super(statusCode, error, exception, message);
    }

    public ExecutionException(String exception) {
        super(statusCode, error, exception, message);
    }

    public ExecutionException() {
        super(statusCode, error, "ExecutionException.class", message);
    }
    
}
