package com.example.wedding_gifts_api.infra.dtos.account;

import java.util.Date;

public record UpdateAccountDTO(
    String brideGroom,
    Date weddingDate,
    String firstName,
    String lastName,
    String password,
    String pixKey
){}
