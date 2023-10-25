package com.example.wedding_gifts.core.domain.dtos.exception;

import java.time.LocalDateTime;

public record ExceptionResponseDTO(
    LocalDateTime timestamp,
    Integer status,
    String error,
    String exception,
    String message,
    String path
){}
