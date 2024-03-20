package com.example.wedding_gifts_api.core.domain.exceptions.change_request;

import com.example.wedding_gifts_api.core.domain.exceptions.common.ExecutionException;

public class ChangeRequestExecutionException extends ExecutionException {

    private static String exception = "ChangeRequestExecutionException.class";

    public ChangeRequestExecutionException(String message, Throwable cause) {
        super(message, exception, cause);
    }

    public ChangeRequestExecutionException(String message) {
        super(message, exception);
    }

    public ChangeRequestExecutionException() {
        super(exception);
    }

    
}
