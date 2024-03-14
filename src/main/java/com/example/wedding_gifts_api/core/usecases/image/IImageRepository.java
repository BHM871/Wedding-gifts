package com.example.wedding_gifts_api.core.usecases.image;

import java.util.Set;
import java.util.UUID;

import com.example.wedding_gifts_api.core.domain.model.Image;
import com.example.wedding_gifts_api.infra.dtos.image.SaveImageDTO;

public interface IImageRepository {
    
    public Image saveImage(SaveImageDTO pathImage) throws Exception;

    public void deleteImageById(UUID image) throws Exception;

    public void deleteImages(Set<Image> images) throws Exception;

    public void deleteAllByGift(UUID giftId) throws Exception;

    public Image getById(UUID image) throws Exception;

    public Set<Image> getAllImagesByGift(UUID giftId);

}
