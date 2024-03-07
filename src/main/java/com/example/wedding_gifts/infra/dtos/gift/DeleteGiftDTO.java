package com.example.wedding_gifts.infra.dtos.gift;

import java.util.UUID;

public record DeleteGiftDTO(
    UUID giftId,
    UUID accountId
){}
