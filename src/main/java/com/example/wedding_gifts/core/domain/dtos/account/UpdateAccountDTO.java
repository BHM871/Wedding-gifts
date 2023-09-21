package com.example.wedding_gifts.core.domain.dtos.account;

public record UpdateAccountDTO(
    String brideAndGroom,
    String firstName,
    String lastName,
    String password,
    String pixKey
){}
