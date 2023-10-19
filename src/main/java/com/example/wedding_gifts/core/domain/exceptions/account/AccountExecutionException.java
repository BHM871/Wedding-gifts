package com.example.wedding_gifts.core.domain.exceptions.account;

import com.example.wedding_gifts.core.domain.exceptions.common.ExecutionException;

public class AccountExecutionException extends ExecutionException {

    private static String exception = "AccountExecutionException.class";

    public AccountExecutionException(String message, Throwable cause) {
        super(message, exception, cause);
    }

    public AccountExecutionException(String message) {
        super(message, exception);
    }

    public AccountExecutionException() {
        super(exception);
    }

    
}
