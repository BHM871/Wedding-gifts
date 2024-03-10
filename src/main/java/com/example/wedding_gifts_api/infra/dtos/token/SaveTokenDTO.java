package com.example.wedding_gifts_api.infra.dtos.token;

import java.time.LocalDateTime;
import java.util.UUID;

public record SaveTokenDTO(
    String token,
    LocalDateTime limitHour,
    UUID accountId
){}
