package com.example.wedding_gifts.core.usecases.image;

import java.util.List;
import java.util.UUID;

import com.example.wedding_gifts.core.domain.dtos.image.DeleteImageDTO;
import com.example.wedding_gifts.core.domain.dtos.image.UpdateImageDTO;

public interface IImageRepository {
    
    public String saveImage(String pathImage) throws Exception;

    public String updateImage(UpdateImageDTO updateImage) throws Exception;

    public String deleteImage(DeleteImageDTO deleteImage) throws Exception;

    public String getById(UUID imageId) throws Exception;

    public List<String> getAllImagesByGift(UUID giftId) throws Exception;

}
