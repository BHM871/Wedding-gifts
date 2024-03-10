package com.example.wedding_gifts_api.infra.dtos.gift.searchers;

import java.util.List;

import com.example.wedding_gifts_api.core.domain.model.util.CategoriesEnum;

public record SearcherByTitleAndCategoriesDTO(
    String title,
    List<CategoriesEnum> categories,
    Boolean isBought
){}
