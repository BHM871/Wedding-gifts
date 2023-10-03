package com.example.wedding_gifts.core.domain.dtos.image;

import java.util.UUID;

import org.springframework.web.multipart.MultipartFile;

public record SaveImageDTO(
    MultipartFile image,
    UUID giftId,
    UUID accountId
){}
