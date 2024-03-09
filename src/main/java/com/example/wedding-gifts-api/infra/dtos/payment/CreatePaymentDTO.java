package com.example.wedding_gifts.infra.dtos.payment;

import com.example.wedding_gifts.core.domain.model.util.MethodOfPayment;

public record CreatePaymentDTO(
    String name,
    String cpf,
    String cnpj,
    MethodOfPayment method
){}
