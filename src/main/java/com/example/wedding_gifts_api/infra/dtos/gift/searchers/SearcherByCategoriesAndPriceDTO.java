package com.example.wedding_gifts_api.infra.dtos.gift.searchers;

import java.math.BigDecimal;
import java.util.List;

import com.example.wedding_gifts_api.core.domain.model.util.CategoriesEnum;

public record SearcherByCategoriesAndPriceDTO(
    List<CategoriesEnum> categories,
    BigDecimal startPrice,
    BigDecimal endPrice,
    Boolean isBought
){}
