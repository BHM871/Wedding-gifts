package com.example.wedding_gifts.core.domain.dtos.oauthpsb;

public record CreateOAuthPsb(
    String access_token,
    String token_type,
    Long expires_in,
    String scope
){}
