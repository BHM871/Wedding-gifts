package com.example.wedding_gifts.core.usecases.image;

import java.util.List;
import java.util.UUID;

import com.example.wedding_gifts.core.domain.dtos.image.DeleteImageDTO;
import com.example.wedding_gifts.core.domain.dtos.image.ImageDTO;

public interface IImageUseCase {
    
    public String saveImage(ImageDTO saveImage) throws Exception;

    public void deleteImage(DeleteImageDTO deleteImage) throws Exception;

    public String getById(UUID imageId) throws Exception;
    
    public UUID getImageIdByPath(String path) throws Exception;

    public List<String> getAllByGift(UUID giftId) throws Exception;

}
