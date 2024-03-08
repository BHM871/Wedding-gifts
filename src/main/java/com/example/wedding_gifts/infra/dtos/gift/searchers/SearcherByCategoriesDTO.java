package com.example.wedding_gifts.infra.dtos.gift.searchers;

import java.util.List;

import com.example.wedding_gifts.core.domain.model.util.CategoriesEnum;

public record SearcherByCategoriesDTO(
    List<CategoriesEnum> categories,
    Boolean isBought
){}