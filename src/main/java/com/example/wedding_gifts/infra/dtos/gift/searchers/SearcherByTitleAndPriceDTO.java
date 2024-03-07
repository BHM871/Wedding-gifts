package com.example.wedding_gifts.infra.dtos.gift.searchers;

import java.math.BigDecimal;

public record SearcherByTitleAndPriceDTO(
    String title,
    BigDecimal startPrice,
    BigDecimal endPrice,
    Boolean isBought
){}
