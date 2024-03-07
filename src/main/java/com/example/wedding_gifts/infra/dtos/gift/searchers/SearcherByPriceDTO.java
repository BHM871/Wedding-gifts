package com.example.wedding_gifts.infra.dtos.gift.searchers;

import java.math.BigDecimal;

public record SearcherByPriceDTO(
    BigDecimal startPrice,
    BigDecimal endPrice,
    Boolean isBought
){}
