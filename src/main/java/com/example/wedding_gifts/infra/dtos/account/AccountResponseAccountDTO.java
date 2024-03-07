package com.example.wedding_gifts.infra.dtos.account;

import java.util.Date;
import java.util.UUID;

public record AccountResponseAccountDTO(
    UUID id,
    String brideGroom, 
    Date weddingDate,
    String firstName,
    String lastName,
    String email
){}
