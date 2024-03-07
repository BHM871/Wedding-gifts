package com.example.wedding_gifts.core.usecases.image;

import java.util.Set;
import java.util.UUID;

import com.example.wedding_gifts.core.domain.model.Image;
import com.example.wedding_gifts.infra.dtos.image.SaveImageDTO;

public interface IImageRepository {
    
    public Image saveImage(SaveImageDTO pathImage) throws Exception;

    public void deleteImages(Set<Image> images) throws Exception;

    public void deleteAllByGift(UUID giftId) throws Exception;

    public Image getById(UUID imageId) throws Exception;

    public Set<Image> getAllImagesByGift(UUID giftId);

}
