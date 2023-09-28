package com.example.wedding_gifts.core.domain.dtos.authentication;

import com.example.wedding_gifts.core.domain.model.Account;

public record CreatedAccountResponseDTO(String token, Account account) {}
