package com.example.wedding_gifts.core.domain.exceptions.gift;

import com.example.wedding_gifts.core.domain.exceptions.common.InvalidValueException;

public class GiftInvalidValueException extends InvalidValueException {

    private static String exception = "GiftInvalidValueException.class";

    public GiftInvalidValueException(String message, Throwable cause) {
        super(message, exception, cause);
    }

    public GiftInvalidValueException(String message) {
        super(message, exception);
    }

    public GiftInvalidValueException() {
        super(exception);
    }
    
}
