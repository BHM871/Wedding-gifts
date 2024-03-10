package com.example.wedding_gifts_api.core.usecases.image;

import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import com.example.wedding_gifts_api.infra.dtos.commun.Base64ResponseDTO;
import com.example.wedding_gifts_api.infra.dtos.commun.MessageDTO;

public interface IImageController {

    public ResponseEntity<Base64ResponseDTO> toBase64(MultipartFile imageFile) throws Exception;

    public ResponseEntity<MessageDTO> insert(String token, UUID account, UUID gift, List<String> images) throws Exception;

    public ResponseEntity<MessageDTO> delete(String token, UUID account, UUID gift, List<UUID> images) throws Exception;
    
}
