package com.example.wedding_gifts.core.domain.dtos.gift;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import com.example.wedding_gifts.core.domain.dtos.account.AccountResponseIdDTO;
import com.example.wedding_gifts.core.domain.model.CategoriesEnum;

public record GiftDTO(
    UUID id,
    String title,
    String giftDescription,
    BigDecimal price,
    List<CategoriesEnum> categories,
    Boolean isBought,
    AccountResponseIdDTO account
){}
