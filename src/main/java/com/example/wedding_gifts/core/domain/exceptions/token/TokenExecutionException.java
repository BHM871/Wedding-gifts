package com.example.wedding_gifts.core.domain.exceptions.token;

import com.example.wedding_gifts.core.domain.exceptions.common.ExecutionException;

public class TokenExecutionException extends ExecutionException {

    private static String exception = "TokenExecutionException.class";

    public TokenExecutionException(String message, Throwable cause) {
        super(message, exception, cause);
    }

    public TokenExecutionException(String message) {
        super(message, exception);
    }

    public TokenExecutionException() {
        super(exception);
    }
    
}
