package com.example.wedding_gifts_api.infra.dtos.gift.searchers;

public record SearcherByTitleDTO(
    String title,
    Boolean isBought
){}
