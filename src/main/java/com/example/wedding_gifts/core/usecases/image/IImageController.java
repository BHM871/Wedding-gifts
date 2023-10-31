package com.example.wedding_gifts.core.usecases.image;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import com.example.wedding_gifts.core.domain.dtos.commun.Base64ResponseDTO;
import com.example.wedding_gifts.core.domain.dtos.commun.MessageDTO;
import com.example.wedding_gifts.core.domain.dtos.image.UpdateImageDTO;

public interface IImageController {

    public ResponseEntity<Base64ResponseDTO> toBase64(MultipartFile imagesFile) throws Exception;

    public ResponseEntity<MessageDTO> updateImages(UpdateImageDTO update) throws Exception;
    
}
