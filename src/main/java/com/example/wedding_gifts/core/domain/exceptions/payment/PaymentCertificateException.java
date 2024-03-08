package com.example.wedding_gifts.core.domain.exceptions.payment;

import com.example.wedding_gifts.core.domain.exceptions.common.ExecutionException;

public class PaymentCertificateException extends ExecutionException {

    private static String exception = "PaymentCertificateException.class";

    public PaymentCertificateException(String message, Throwable cause) {
        super(message, exception, cause);
    }

    public PaymentCertificateException(String message) {
        super(message, exception);
    }

    public PaymentCertificateException() {
        super(exception);
    }
    
}
