package com.example.wedding_gifts.core.domain.dtos.gift;

import java.util.List;

import com.example.wedding_gifts.core.domain.dtos.image.ImageResponseDTO;

public record GiftResponseDTO(
    GiftDTO gift,
    List<ImageResponseDTO> imagesPath
){}
