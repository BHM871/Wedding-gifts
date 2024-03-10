package com.example.wedding_gifts_api.core.domain.exceptions.account;

import com.example.wedding_gifts_api.core.domain.exceptions.common.InvalidValueException;

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
