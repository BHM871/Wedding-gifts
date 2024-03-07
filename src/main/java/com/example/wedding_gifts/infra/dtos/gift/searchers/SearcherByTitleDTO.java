package com.example.wedding_gifts.infra.dtos.gift.searchers;

public record SearcherByTitleDTO(
    String title,
    Boolean isBought
){}
