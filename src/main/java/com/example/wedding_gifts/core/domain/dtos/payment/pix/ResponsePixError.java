package com.example.wedding_gifts.core.domain.dtos.payment.pix;

public record ResponsePixError(
    String title,
    String status,
    String detail
){}
