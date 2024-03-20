package com.example.wedding_gifts_api.infra.dtos.authentication;

public record ChangePassLoggedDTO(
    String email,
    String lastPass,
    String newPass
){}
