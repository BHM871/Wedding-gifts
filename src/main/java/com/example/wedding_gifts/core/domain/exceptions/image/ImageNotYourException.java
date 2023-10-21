package com.example.wedding_gifts.core.domain.exceptions.image;

import com.example.wedding_gifts.core.domain.exceptions.common.NotYourException;

public class ImageNotYourException extends NotYourException {

    private static String exception = "ImageNotYourException.class";

    public ImageNotYourException(String message, Throwable cause) {
        super(message, exception, cause);
    }

    public ImageNotYourException(String message) {
        super(message, exception);
    }

    public ImageNotYourException() {
        super(exception);
    }
    
}
