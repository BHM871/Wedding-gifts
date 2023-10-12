package com.example.wedding_gifts.core.domain.dtos.token;

import java.time.LocalDateTime;

public record SaveTokenDTO(
    String token,
    LocalDateTime limitHour
){}
