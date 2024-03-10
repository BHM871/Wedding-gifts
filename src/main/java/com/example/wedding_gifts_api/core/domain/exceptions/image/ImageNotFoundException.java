package com.example.wedding_gifts_api.core.domain.exceptions.image;

import com.example.wedding_gifts_api.core.domain.exceptions.common.NotFoundException;

public class ImageNotFoundException extends NotFoundException {

    private static String exception = "ImageNotFoundException.class";

    public ImageNotFoundException(String message, Throwable cause) {
        super(message, exception, cause);
    }

    public ImageNotFoundException(String message) {
        super(message, exception);
    }

    public ImageNotFoundException() {
        super(exception);
    }
    
}
