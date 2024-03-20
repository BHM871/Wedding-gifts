package com.example.wedding_gifts_api.core.domain.exceptions.change_request;

import com.example.wedding_gifts_api.core.domain.exceptions.common.NotFoundException;

public class ChangeRequestNotFoundException extends NotFoundException {

    private static String exception = "ChangeRequestNotFoundException.class";

    public ChangeRequestNotFoundException(String message, Throwable cause) {
        super(message, exception, cause);
    }

    public ChangeRequestNotFoundException(String message) {
        super(message, exception);
    }

    public ChangeRequestNotFoundException() {
        super(exception);
    }

}