package com.example.wedding_gifts.infra.dtos.oauthpsb;

public record CreateOAuthPsb(
    String access_token,
    String token_type,
    Long expires_in,
    String scope
){}
