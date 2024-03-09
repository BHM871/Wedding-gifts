package com.example.wedding_gifts.core.domain.exceptions.account;

import com.example.wedding_gifts.core.domain.exceptions.common.ForbiddenException;

public class AccountForbiddenException extends ForbiddenException {

    private static String exception = "AccountForbiddenException.class";

    public AccountForbiddenException(String message, Throwable cause) {
        super(message, exception, cause);
    }

    public AccountForbiddenException(String message) {
        super(message, exception);
    }

    public AccountForbiddenException() {
        super(exception);
    }
    
}
