package com.example.wedding_gifts_api.core.domain.exceptions.change_request;

import com.example.wedding_gifts_api.core.domain.exceptions.common.InvalidValueException;

public class ChangeRequestInvalidValueException extends InvalidValueException {

    private static String exception = "ChangeRequestInvalidValueException.class";

    public ChangeRequestInvalidValueException(String message, Throwable cause) {
        super(message, exception, cause);
    }

    public ChangeRequestInvalidValueException(String message) {
        super(message, exception);
    }

    public ChangeRequestInvalidValueException() {
        super(exception);
    }

}
