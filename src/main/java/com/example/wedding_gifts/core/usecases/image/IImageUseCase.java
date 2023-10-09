package com.example.wedding_gifts.core.usecases.image;

import java.util.List;
import java.util.UUID;

import com.example.wedding_gifts.core.domain.dtos.image.DeleteImageDTO;
import com.example.wedding_gifts.core.domain.dtos.image.ImageDTO;
import com.example.wedding_gifts.core.domain.model.Image;

public interface IImageUseCase {
    
    public Image saveImage(ImageDTO saveImage) throws Exception;

    public void deleteImage(DeleteImageDTO deleteImage) throws Exception;

    public void deleteAllByGift(UUID giftId) throws Exception;

    public Image getById(UUID imageId) throws Exception;

    public List<Image> getAllByGift(UUID giftId) throws Exception;

}
