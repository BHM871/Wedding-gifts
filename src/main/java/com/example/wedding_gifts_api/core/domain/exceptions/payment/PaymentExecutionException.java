package com.example.wedding_gifts_api.core.domain.exceptions.payment;

import com.example.wedding_gifts_api.core.domain.exceptions.common.ExecutionException;

public class PaymentExecutionException extends ExecutionException {

    private static String exception = "PaymentExecutionException.class";

    public PaymentExecutionException(String message, Throwable cause) {
        super(message, exception, cause);
    }

    public PaymentExecutionException(String message) {
        super(message, exception);
    }

    public PaymentExecutionException() {
        super(exception);
    }
    
}
