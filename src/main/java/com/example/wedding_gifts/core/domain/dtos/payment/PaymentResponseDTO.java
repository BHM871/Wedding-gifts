package com.example.wedding_gifts.core.domain.dtos.payment;

import java.time.LocalDateTime;

public record PaymentResponseDTO(
    String description,
    String qrcode,
    LocalDateTime expiration
){}
