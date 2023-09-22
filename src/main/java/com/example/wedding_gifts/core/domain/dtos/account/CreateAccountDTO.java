package com.example.wedding_gifts.core.domain.dtos.account;

public record CreateAccountDTO(
    String firstName, 
    String lastName, 
    String brideGroom,
    String email, 
    String password,
    String pixKey
){}
