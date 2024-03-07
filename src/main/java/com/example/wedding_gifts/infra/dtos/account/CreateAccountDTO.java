package com.example.wedding_gifts.infra.dtos.account;

import java.util.Date;

public record CreateAccountDTO(
    String firstName, 
    String lastName, 
    String brideGroom,
    Date weddingDate,
    String email, 
    String password,
    String pixKey
){}
