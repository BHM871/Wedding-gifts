package com.example.wedding_gifts.core.domain.dtos.gift;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import com.example.wedding_gifts.core.domain.model.util.CategoriesEnum;

public record UpdateGiftDTO(
    UUID giftId,
    UUID accountId,
    String title,
    String giftDescription,
    List<CategoriesEnum> categories,
    BigDecimal price,
    Boolean isBought
){}
