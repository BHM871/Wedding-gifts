package com.example.wedding_gifts.core.domain.dtos.image;

import java.util.UUID;

public record DeleteImageDTO(
    String pathImage,
    UUID imageId,
    UUID giftId,
    UUID accountId
){}
