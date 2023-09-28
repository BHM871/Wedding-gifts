package com.example.wedding_gifts.core.domain.dtos.gift;

import java.math.BigDecimal;

public record SearcherByPriceDTO(
    BigDecimal startPrice,
    BigDecimal endPrice,
    Boolean isBought
){}
