package com.example.wedding_gifts.core.domain.exceptions.token;

import com.example.wedding_gifts.core.domain.exceptions.common.InvalidValueException;

public class TokenInvalidValueException extends InvalidValueException {

    private static String exception = "TokenInvalidValueException.class";

    public TokenInvalidValueException(String message, Throwable cause) {
        super(message, exception, cause);
    }

    public TokenInvalidValueException(String message) {
        super(message, exception);
    }

    public TokenInvalidValueException() {
        super(exception);
    }
    
}
