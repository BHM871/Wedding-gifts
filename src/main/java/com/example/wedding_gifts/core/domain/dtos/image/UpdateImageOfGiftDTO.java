package com.example.wedding_gifts.core.domain.dtos.image;

import java.util.List;
import java.util.UUID;

import org.springframework.web.multipart.MultipartFile;

public record UpdateImageOfGiftDTO(
    List<UUID> imagesPath,
    List<MultipartFile> imagesFile
){}
