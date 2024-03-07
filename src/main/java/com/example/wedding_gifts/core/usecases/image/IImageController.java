package com.example.wedding_gifts.core.usecases.image;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import com.example.wedding_gifts.infra.dtos.commun.Base64ResponseDTO;
import com.example.wedding_gifts.infra.dtos.commun.MessageDTO;
import com.example.wedding_gifts.infra.dtos.image.DeleteImagesDTO;
import com.example.wedding_gifts.infra.dtos.image.InsertImagesDTO;

public interface IImageController {

    public ResponseEntity<Base64ResponseDTO> toBase64(MultipartFile imageFile) throws Exception;

    public ResponseEntity<MessageDTO> insert(InsertImagesDTO update) throws Exception;

    public ResponseEntity<MessageDTO> delete(DeleteImagesDTO update) throws Exception;
    
}
