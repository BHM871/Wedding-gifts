package com.example.wedding_gifts.adapters.payment;

import java.util.UUID;

import com.example.wedding_gifts.core.domain.dtos.payment.pix.CreatePixDTO;
import com.example.wedding_gifts.core.domain.model.Payment;

public interface PaymentAdapter {

    public Payment createPayment(CreatePixDTO payment) throws Exception;

    public Payment checkPayment(UUID paymentId) throws Exception;
    
}
