package com.example.wedding_gifts_api.core.domain.exceptions.change_request;

import com.example.wedding_gifts_api.core.domain.exceptions.common.NotNullableException;

public class ChangeRequestNotNullableException extends NotNullableException {

    private static String exception = "ChangeRequestNotNullableException.class";

    public ChangeRequestNotNullableException(String message, Throwable cause) {
        super(message, exception, cause);
    }

    public ChangeRequestNotNullableException(String message) {
        super(message, exception);
    }

    public ChangeRequestNotNullableException() {
        super(exception);
    }
    
}
