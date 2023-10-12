package com.example.wedding_gifts.core.domain.dtos.token;

import java.util.UUID;

public record ValidTokenDTO(
    String token,
    UUID accountId
){}
