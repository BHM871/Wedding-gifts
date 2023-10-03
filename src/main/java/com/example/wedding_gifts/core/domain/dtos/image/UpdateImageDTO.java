package com.example.wedding_gifts.core.domain.dtos.image;

import java.util.UUID;

import org.springframework.web.multipart.MultipartFile;

public record UpdateImageDTO(
    MultipartFile image,
    UUID imageId,
    UUID giftId,
    UUID accountId
){}
