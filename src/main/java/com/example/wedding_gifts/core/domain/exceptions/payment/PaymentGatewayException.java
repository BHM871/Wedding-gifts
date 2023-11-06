package com.example.wedding_gifts.core.domain.exceptions.payment;

import com.example.wedding_gifts.core.domain.exceptions.common.GatewayException;

public class PaymentGatewayException extends GatewayException {

    private static String exception = "PaymentGatewayException.class";

    public PaymentGatewayException(String message, Throwable cause) {
        super(message, exception, cause);
    }

    public PaymentGatewayException(String message) {
        super(message, exception);
    }

    public PaymentGatewayException() {
        super(exception);
    }
    
}
