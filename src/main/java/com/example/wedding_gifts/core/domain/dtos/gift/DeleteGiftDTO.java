package com.example.wedding_gifts.core.domain.dtos.gift;

import java.util.UUID;

public record DeleteGiftDTO(
    UUID giftId,
    UUID accountId
){}
