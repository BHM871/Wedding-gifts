package com.example.wedding_gifts_api.core.domain.exceptions.gift;

import com.example.wedding_gifts_api.core.domain.exceptions.common.NotYourException;

public class GiftNotYourException extends NotYourException {

    private static String exception = "GiftNotYourException.class";

    public GiftNotYourException(String message, Throwable cause) {
        super(message, exception, cause);
    }

    public GiftNotYourException(String message) {
        super(message, exception);
    }

    public GiftNotYourException() {
        super(exception);
    }
    
}
