package com.example.wedding_gifts.core.domain.dtos.token;

import java.time.LocalDateTime;
import java.util.UUID;

public record SaveTokenDTO(
    String token,
    LocalDateTime limitHour,
    UUID accountId
){}
