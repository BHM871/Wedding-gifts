package com.example.wedding_gifts_api.core.domain.exceptions.gift;

import com.example.wedding_gifts_api.core.domain.exceptions.common.NotNullableException;

public class GiftNotNullableException extends NotNullableException {

    private static String exception = "GiftNotNullableException.class";

    public GiftNotNullableException(String message, Throwable cause) {
        super(message, exception, cause);
    }

    public GiftNotNullableException(String message) {
        super(message, exception);
    }

    public GiftNotNullableException() {
        super(exception);
    }
    
}
