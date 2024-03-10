package com.example.wedding_gifts_api.infra.dtos.gift.searchers;

import java.math.BigDecimal;

public record SearcherByPriceDTO(
    BigDecimal startPrice,
    BigDecimal endPrice,
    Boolean isBought
){}
