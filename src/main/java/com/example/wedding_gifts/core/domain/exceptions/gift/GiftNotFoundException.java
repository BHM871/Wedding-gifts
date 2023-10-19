package com.example.wedding_gifts.core.domain.exceptions.gift;

import com.example.wedding_gifts.core.domain.exceptions.commun.NotFoundException;

public class GiftNotFoundException extends NotFoundException {

    private static String exception = "GiftNotFoundException.class";

    public GiftNotFoundException(String message, Throwable cause) {
        super(message, exception, cause);
    }

    public GiftNotFoundException(String message) {
        super(message, exception);
    }

    public GiftNotFoundException() {
        super(exception);
    }
    
}
