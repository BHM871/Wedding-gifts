package com.example.wedding_gifts_api.core.domain.exceptions.common;

public abstract class GatewayException extends MyException {

    private static int statusCode = 502;
    private static String error = "Bad Gateway";
    private static String message = "Some error with a Gateway";

    public GatewayException(String message, String exception, Throwable cause) {
        super(cause, statusCode, error, exception, message);
    }

    public GatewayException(String message, String exception) {
        super(statusCode, error, exception, message);
    }

    public GatewayException(String exception) {
        super(statusCode, error, exception, message);
    }

    public GatewayException() {
        super(statusCode, error, "GatewayException.class", message);
    }
    
}
