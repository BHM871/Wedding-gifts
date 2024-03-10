package com.example.wedding_gifts_api.core.domain.exceptions.image;

import com.example.wedding_gifts_api.core.domain.exceptions.common.NotYourException;

public class ImageNotNullableException extends NotYourException {

    private static String exception = "ImageNotNullableException.class";

    public ImageNotNullableException(String message, Throwable cause) {
        super(message, exception, cause);
    }

    public ImageNotNullableException(String message) {
        super(message, exception);
    }

    public ImageNotNullableException() {
        super(exception);
    }
    
}
