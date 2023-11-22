package com.example.wedding_gifts.core.domain.dtos.oauthpsb;

public record CreateOAuthPsb(
    String token,
    Long expiration
){}
