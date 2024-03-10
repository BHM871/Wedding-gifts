package com.example.wedding_gifts_api.infra.dtos.gift.searchers;

import java.math.BigDecimal;

public record SearcherByTitleAndPriceDTO(
    String title,
    BigDecimal startPrice,
    BigDecimal endPrice,
    Boolean isBought
){}
