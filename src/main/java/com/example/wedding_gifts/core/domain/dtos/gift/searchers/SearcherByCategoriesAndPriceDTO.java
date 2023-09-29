package com.example.wedding_gifts.core.domain.dtos.gift.searchers;

import java.math.BigDecimal;
import java.util.List;

import com.example.wedding_gifts.core.domain.model.CategoriesEnum;

public record SearcherByCategoriesAndPriceDTO(
    List<CategoriesEnum> categories,
    BigDecimal startPrice,
    BigDecimal endPrice,
    Boolean isBought
){}
