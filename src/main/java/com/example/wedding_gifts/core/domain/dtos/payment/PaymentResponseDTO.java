package com.example.wedding_gifts.core.domain.dtos.payment;

import org.springframework.web.multipart.MultipartFile;

public record PaymentResponseDTO(
    String description,
    String qrcode,
    MultipartFile paymentFile
){}
