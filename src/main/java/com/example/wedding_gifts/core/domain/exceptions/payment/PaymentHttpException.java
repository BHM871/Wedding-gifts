package com.example.wedding_gifts.core.domain.exceptions.payment;

import com.example.wedding_gifts.core.domain.exceptions.common.ExecutionException;

public class PaymentHttpException extends ExecutionException {

    private static String exception = "PaymentHttpException.class";

    public PaymentHttpException(String message, Throwable cause) {
        super(message, exception, cause);
    }

    public PaymentHttpException(String message) {
        super(message, exception);
    }

    public PaymentHttpException() {
        super(exception);
    }
    
}
