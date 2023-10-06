package com.example.wedding_gifts.core.domain.dtos.gift;

import java.util.List;

import com.example.wedding_gifts.core.domain.model.Gift;
import com.example.wedding_gifts.core.domain.model.Image;

public record GiftResponseDTO(
    Gift gift,
    List<Image> imagesPath
){}
