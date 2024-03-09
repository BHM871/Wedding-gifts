package com.example.wedding_gifts.core.domain.exceptions.image;

import com.example.wedding_gifts.core.domain.exceptions.common.InvalidValueException;

public class ImageInvalidValueException extends InvalidValueException {

    private static String exception = "ImageInvalidValueException.class";

    public ImageInvalidValueException(String message, Throwable cause) {
        super(message, exception, cause);
    }

    public ImageInvalidValueException(String message) {
        super(message, exception);
    }

    public ImageInvalidValueException() {
        super(exception);
    }
    
}
