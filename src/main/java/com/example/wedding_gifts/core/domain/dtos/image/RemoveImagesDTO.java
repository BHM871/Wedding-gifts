package com.example.wedding_gifts.core.domain.dtos.image;

import java.util.List;
import java.util.UUID;

public record RemoveImagesDTO(
    UUID giftId,
    UUID accountId,
    List<UUID> imagesId
){}
