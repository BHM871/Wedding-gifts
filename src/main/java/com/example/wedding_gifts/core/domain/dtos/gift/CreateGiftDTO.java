package com.example.wedding_gifts.core.domain.dtos.gift;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import org.springframework.web.multipart.MultipartFile;

import com.example.wedding_gifts.core.domain.model.CategoriesEnum;

public record CreateGiftDTO(
    String title,
    String giftDescription,
    List<CategoriesEnum> categories,
    BigDecimal price,
    List<MultipartFile> images,
    UUID accountId
){}
