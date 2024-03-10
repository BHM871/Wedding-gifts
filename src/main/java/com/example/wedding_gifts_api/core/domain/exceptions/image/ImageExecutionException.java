package com.example.wedding_gifts_api.core.domain.exceptions.image;

import com.example.wedding_gifts_api.core.domain.exceptions.common.ExecutionException;

public class ImageExecutionException extends ExecutionException {

    private static String exception = "ImageExecutionException.class";

    public ImageExecutionException(String message, Throwable cause) {
        super(message, exception, cause);
    }

    public ImageExecutionException(String message) {
        super(message, exception);
    }

    public ImageExecutionException() {
        super(exception);
    }
    
}
