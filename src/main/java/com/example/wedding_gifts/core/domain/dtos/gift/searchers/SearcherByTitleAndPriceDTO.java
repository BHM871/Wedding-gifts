package com.example.wedding_gifts.core.domain.dtos.gift.searchers;

import java.math.BigDecimal;

public record SearcherByTitleAndPriceDTO(
    String title,
    BigDecimal startPrice,
    BigDecimal endPrice,
    Boolean isBought
){}
