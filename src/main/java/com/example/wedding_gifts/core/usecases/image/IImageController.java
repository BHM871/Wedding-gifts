package com.example.wedding_gifts.core.usecases.image;

import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import com.example.wedding_gifts.infra.dtos.commun.Base64ResponseDTO;
import com.example.wedding_gifts.infra.dtos.commun.MessageDTO;

public interface IImageController {

    public ResponseEntity<Base64ResponseDTO> toBase64(MultipartFile imageFile) throws Exception;

    public ResponseEntity<MessageDTO> insert(String token, UUID accountId, UUID giftId, List<String> images) throws Exception;

    public ResponseEntity<MessageDTO> delete(String token, UUID accountId, UUID giftId, List<UUID> images) throws Exception;
    
}
