package com.example.wedding_gifts.core.domain.exceptions.payment;

import com.example.wedding_gifts.core.domain.exceptions.common.NotFoundException;

public class PaymentNotFoundException extends NotFoundException {

    private static String exception = "PaymentNotFoundException.class";

    public PaymentNotFoundException(String message, Throwable cause) {
        super(message, exception, cause);
    }

    public PaymentNotFoundException(String message) {
        super(message, exception);
    }

    public PaymentNotFoundException() {
        super(exception);
    }
    
}
