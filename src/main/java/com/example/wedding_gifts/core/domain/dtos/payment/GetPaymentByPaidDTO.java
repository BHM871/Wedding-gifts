package com.example.wedding_gifts.core.domain.dtos.payment;

import java.util.UUID;

public record GetPaymentByPaidDTO(
    UUID accountId,
    Boolean isPaid
){}
