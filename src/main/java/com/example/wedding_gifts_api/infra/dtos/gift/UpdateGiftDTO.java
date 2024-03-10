package com.example.wedding_gifts_api.infra.dtos.gift;

import java.math.BigDecimal;
import java.util.List;

import com.example.wedding_gifts_api.core.domain.model.util.CategoriesEnum;

public record UpdateGiftDTO(
    String title,
    String giftDescription,
    List<CategoriesEnum> categories,
    BigDecimal price,
    Boolean isBought
){}
