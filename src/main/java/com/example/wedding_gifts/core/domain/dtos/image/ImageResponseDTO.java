package com.example.wedding_gifts.core.domain.dtos.image;

import java.util.UUID;

public record ImageResponseDTO(
    UUID id,
    String imagePath
){}
