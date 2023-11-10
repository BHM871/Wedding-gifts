package com.example.wedding_gifts.adapters.payment;

import com.example.wedding_gifts.core.domain.dtos.payment.CreatePaymentDTO;
import com.example.wedding_gifts.core.domain.model.Payment;

public interface PaymentAdapter {

    public Payment createPayment(CreatePaymentDTO payment) throws Exception;

    public Payment checkPayment(Payment payment) throws Exception;

    public String getPaymentCode(Payment payment) throws Exception;
    
}
