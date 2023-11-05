package com.example.wedding_gifts.core.domain.dtos.payment;

import java.util.UUID;

import com.example.wedding_gifts.core.domain.model.util.MethodOfPayment;

public record CreatePaymentDTO(
    String name,
    String cpf,
    MethodOfPayment method,
    UUID giftId
){}
