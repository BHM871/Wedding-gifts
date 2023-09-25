package com.example.wedding_gifts.core.domain.dtos.account;

import java.util.Date;

public record UpdateAccountDTO(
    String brideGroom,
    Date weddingDate,
    String firstName,
    String lastName,
    String password,
    String pixKey
){}
