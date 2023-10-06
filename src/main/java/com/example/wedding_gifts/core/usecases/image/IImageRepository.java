package com.example.wedding_gifts.core.usecases.image;

import java.util.List;
import java.util.UUID;

import com.example.wedding_gifts.core.domain.dtos.image.DeleteImageDTO;
import com.example.wedding_gifts.core.domain.dtos.image.SaveImageDTO;
import com.example.wedding_gifts.core.domain.model.Image;

public interface IImageRepository {
    
    public String saveImage(SaveImageDTO pathImage) throws Exception;

    public void deleteImage(DeleteImageDTO deleteImage) throws Exception;

    public Image getById(UUID imageId) throws Exception;

    public Image getByPath(String path) throws Exception;

    public List<Image> getAllImagesByGift(UUID giftId) throws Exception;

}
