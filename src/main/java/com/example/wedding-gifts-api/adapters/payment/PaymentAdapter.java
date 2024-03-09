package com.example.wedding_gifts.adapters.payment;

import java.util.UUID;

import com.example.wedding_gifts.core.domain.model.Payment;
import com.example.wedding_gifts.infra.dtos.payment.CreatePaymentDTO;

public interface PaymentAdapter {

    public Payment createPayment(UUID giftId, CreatePaymentDTO payment) throws Exception;

    public Payment checkPayment(Payment payment) throws Exception;

    public String getPaymentCode(Payment payment) throws Exception;
    
}
