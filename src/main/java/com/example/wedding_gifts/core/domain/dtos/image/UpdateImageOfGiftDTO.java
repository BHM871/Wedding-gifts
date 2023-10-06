package com.example.wedding_gifts.core.domain.dtos.image;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

public record UpdateImageOfGiftDTO(
    List<String> imagesPath,
    List<MultipartFile> imagesFile
){}
