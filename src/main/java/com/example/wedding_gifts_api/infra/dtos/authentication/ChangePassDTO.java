package com.example.wedding_gifts_api.infra.dtos.authentication;

public record ChangePassDTO(
    String password,
    String email
){}
