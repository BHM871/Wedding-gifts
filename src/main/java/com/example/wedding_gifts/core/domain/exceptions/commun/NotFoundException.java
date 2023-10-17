package com.example.wedding_gifts.core.domain.exceptions.commun;

public abstract class NotFoundException extends MyException {

    public NotFoundException(String message, String exception, Throwable cause) {
        super(cause, 422, exception, message);
    }

    public NotFoundException(String message, String exception) {
        super(422, exception, message);
    }

    public NotFoundException(String exception) {
        super(422, exception, "Not Found");
    }

    public NotFoundException() {
        super(422, "NotFoundException.class", "Not Found");
    }

}
