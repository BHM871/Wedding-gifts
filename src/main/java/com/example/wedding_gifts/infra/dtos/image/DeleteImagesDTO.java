package com.example.wedding_gifts.infra.dtos.image;

import java.util.List;
import java.util.UUID;

public record DeleteImagesDTO(
    UUID giftId,
    UUID accountId,
    List<UUID> images
){}
