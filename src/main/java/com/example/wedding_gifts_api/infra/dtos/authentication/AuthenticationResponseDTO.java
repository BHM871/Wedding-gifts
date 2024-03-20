package com.example.wedding_gifts_api.infra.dtos.authentication;

import java.time.LocalDateTime;

public record AuthenticationResponseDTO(
    String type,
    String token,
    LocalDateTime limitHour
) {}
