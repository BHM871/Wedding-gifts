package com.example.wedding_gifts.core.domain.dtos.gift;

import java.util.List;

import com.example.wedding_gifts.core.domain.model.CategoriesEnum;

public record SearcherByCategoriesDTO(
    List<CategoriesEnum> categories,
    Boolean isBought
){}
