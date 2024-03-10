package com.example.wedding_gifts_api.core.domain.exceptions.gift;

import com.example.wedding_gifts_api.core.domain.exceptions.common.ExecutionException;

public class GiftExecutionException extends ExecutionException {

    private static String exception = "GiftExecutionException.class";

    public GiftExecutionException(String message, Throwable cause) {
        super(message, exception, cause);
    }

    public GiftExecutionException(String message) {
        super(message, exception);
    }

    public GiftExecutionException() {
        super(exception);
    }
    
}
