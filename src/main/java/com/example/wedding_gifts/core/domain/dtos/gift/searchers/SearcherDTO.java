package com.example.wedding_gifts.core.domain.dtos.gift.searchers;

import java.math.BigDecimal;
import java.util.List;

import com.example.wedding_gifts.core.domain.model.CategoriesEnum;

public record SearcherDTO(
    Boolean isBought,
    String title,
    BigDecimal startPrice,
    BigDecimal endPrice,
    List<CategoriesEnum> categories
){}
