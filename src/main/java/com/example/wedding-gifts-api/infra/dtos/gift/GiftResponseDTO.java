package com.example.wedding_gifts.infra.dtos.gift;

import java.util.List;

import com.example.wedding_gifts.infra.dtos.image.ImageResponseDTO;

public record GiftResponseDTO(
    GiftDTO gift,
    List<ImageResponseDTO> imagesPath
){}
