package com.example.wedding_gifts.infra.dtos.payment;

import java.util.UUID;

public record GetPaymentByPaidDTO(
    UUID accountId,
    Boolean isPaid
){}
