package com.example.wedding_gifts_api.core.domain.exceptions.account;

import com.example.wedding_gifts_api.core.domain.exceptions.common.NotNullableException;

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
