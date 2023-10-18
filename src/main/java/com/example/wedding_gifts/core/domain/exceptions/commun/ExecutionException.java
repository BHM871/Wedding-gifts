package com.example.wedding_gifts.core.domain.exceptions.commun;

public class ExecutionException extends MyException {

    private static int statusCode = 400;

    public ExecutionException(String message, String exception, Throwable cause) {
        super(cause, statusCode, exception, message);
    }

    public ExecutionException(String message, String exception) {
        super(statusCode, exception, message);
    }

    public ExecutionException(String exception) {
        super(statusCode, exception, "Not Found");
    }

    public ExecutionException() {
        super(statusCode, "ExecutionException.class", "Not Found");
    }
    
}
