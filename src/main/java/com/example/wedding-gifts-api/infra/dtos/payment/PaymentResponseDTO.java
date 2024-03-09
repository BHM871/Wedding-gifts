package com.example.wedding_gifts.infra.dtos.payment;

import java.time.LocalDateTime;
import java.util.UUID;

public record PaymentResponseDTO(
    UUID paymentId,
    String transactionId,
    String description,
    String qrcode,
    LocalDateTime expiration
){}
