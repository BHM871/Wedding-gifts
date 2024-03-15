package com.example.wedding_gifts_api.infra.dtos.payment;

import java.time.LocalDateTime;
import java.util.UUID;
import java.math.BigDecimal;

import com.example.wedding_gifts_api.core.domain.model.util.MethodOfPayment;

public record PaymentResponseDTO(
    UUID id,
    UUID giftId,
    MethodOfPayment method,
    String transactionId,
    BigDecimal value,
    String description,
    String code,
    LocalDateTime creation,
    LocalDateTime expiration
){}

