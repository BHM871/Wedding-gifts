package com.example.wedding_gifts_api.core.domain.exceptions.account;

import com.example.wedding_gifts_api.core.domain.exceptions.common.NotFoundException;

public class AccountNotFoundException extends NotFoundException {

    private static String exception = "AccountNotFoundException.class";

    public AccountNotFoundException(String message, Throwable cause) {
        super(message, exception, cause);
    }

    public AccountNotFoundException(String message) {
        super(message, exception);
    }

    public AccountNotFoundException() {
        super(exception);
    }
    
}
