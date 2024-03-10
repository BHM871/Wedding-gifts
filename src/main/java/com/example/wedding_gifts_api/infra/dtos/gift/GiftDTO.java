package com.example.wedding_gifts_api.infra.dtos.gift;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import com.example.wedding_gifts_api.core.domain.model.util.CategoriesEnum;
import com.example.wedding_gifts_api.infra.dtos.account.AccountResponseIdDTO;

public record GiftDTO(
    UUID id,
    String title,
    String giftDescription,
    BigDecimal price,
    List<CategoriesEnum> categories,
    Boolean isBought,
    AccountResponseIdDTO account
){}
