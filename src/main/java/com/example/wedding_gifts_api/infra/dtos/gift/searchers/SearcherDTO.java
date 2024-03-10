package com.example.wedding_gifts_api.infra.dtos.gift.searchers;

import java.math.BigDecimal;
import java.util.List;

import com.example.wedding_gifts_api.core.domain.model.util.CategoriesEnum;

public record SearcherDTO(
    Boolean isBought,
    String title,
    BigDecimal startPrice,
    BigDecimal endPrice,
    List<CategoriesEnum> categories
){}
