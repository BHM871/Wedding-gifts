package com.example.wedding_gifts.core.domain.dtos.gift;

import java.util.List;

import com.example.wedding_gifts.core.domain.dtos.image.ImageResponseDTO;
import com.example.wedding_gifts.core.domain.model.Gift;

public record GiftResponseDTO(
    Gift gift,
    List<ImageResponseDTO> imagesPath
){}
