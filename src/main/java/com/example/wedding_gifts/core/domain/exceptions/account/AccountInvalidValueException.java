package com.example.wedding_gifts.core.domain.exceptions.account;

import com.example.wedding_gifts.core.domain.exceptions.commun.InvalidValueException;

public class AccountInvalidValueException extends InvalidValueException {

    private static String exception = "AccountInvalidValueException.class";

    public AccountInvalidValueException(String message, Throwable cause) {
        super(message, exception, cause);
    }

    public AccountInvalidValueException(String message) {
        super(message, exception);
    }

    public AccountInvalidValueException() {
        super(exception);
    }
    
}
