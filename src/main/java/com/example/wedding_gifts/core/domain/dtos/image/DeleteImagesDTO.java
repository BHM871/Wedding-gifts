package com.example.wedding_gifts.core.domain.dtos.image;

import java.util.List;
import java.util.UUID;

public record DeleteImagesDTO(
    List<UUID> images,
    UUID giftId,
    UUID accountId
){}
