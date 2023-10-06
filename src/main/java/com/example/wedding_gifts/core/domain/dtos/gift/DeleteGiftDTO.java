package com.example.wedding_gifts.core.domain.dtos.gift;

import java.util.List;
import java.util.UUID;

public record DeleteGiftDTO(
    List<UUID> images,
    UUID giftId,
    UUID accountId
){}
