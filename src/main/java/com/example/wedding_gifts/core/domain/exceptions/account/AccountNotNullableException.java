package com.example.wedding_gifts.core.domain.exceptions.account;

import com.example.wedding_gifts.core.domain.exceptions.commun.NotNullableException;

public class AccountNotNullableException extends NotNullableException {

    private static String exception = "AccountNotNullableException.class";

    public AccountNotNullableException(String message, Throwable cause) {
        super(message, exception, cause);
    }

    public AccountNotNullableException(String message) {
        super(message, exception);
    }

    public AccountNotNullableException() {
        super(exception);
    }

}
