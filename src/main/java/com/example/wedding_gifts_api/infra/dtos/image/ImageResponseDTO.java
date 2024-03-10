package com.example.wedding_gifts_api.infra.dtos.image;

import java.util.UUID;

public record ImageResponseDTO(
    UUID id,
    String imagePath
){}
