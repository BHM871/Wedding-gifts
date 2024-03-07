package com.example.wedding_gifts.infra.dtos.image;

import java.util.List;
import java.util.UUID;

public record DeleteImagesDTO(
    List<UUID> images,
    UUID giftId,
    UUID accountId
){}
