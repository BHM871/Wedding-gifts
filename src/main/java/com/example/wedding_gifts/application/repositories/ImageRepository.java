package com.example.wedding_gifts.application.repositories;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.example.wedding_gifts.core.domain.dtos.image.DeleteImageDTO;
import com.example.wedding_gifts.core.domain.dtos.image.SaveImageDTO;
import com.example.wedding_gifts.core.domain.model.Gift;
import com.example.wedding_gifts.core.domain.model.Image;
import com.example.wedding_gifts.core.usecases.image.IImageRepository;
import com.example.wedding_gifts.infra.jpa.JpaImageRespository;

@Repository
public class ImageRepository implements IImageRepository {

    @Autowired
    private JpaImageRespository thisJpaRespository;
    @Autowired
    private GiftRepository giftRepository;

    @Override
    public Image saveImage(SaveImageDTO image) throws Exception {
        Image newImage = new Image(image);
        Gift gift = giftRepository.getGiftById(image.giftId());

        newImage.setGift(gift);

        return thisJpaRespository.save(newImage);
    }

    @Override
    public void deleteImage(DeleteImageDTO deleteImage) throws Exception {
        Image image = getById(deleteImage.imageId());
        
        int compared = image.getGift().getId()
                            .compareTo(deleteImage.giftId());
        if(compared != 0) throw new Exception("This image is not this gift");
        
        compared = image.getGift().getAccount().getId()
                            .compareTo(deleteImage.accountId());
        if(compared != 0) throw new Exception("This gift is not your");

        thisJpaRespository.delete(image);
    }

    @Override
    public void deleteAllByGift(UUID giftId) throws Exception {
        giftRepository.getGiftById(giftId);
        
        List<Image> images = getAllImagesByGift(giftId);

        if(images != null && !images.isEmpty()) thisJpaRespository.deleteAll(images);
    }

    @Override
    public Image getById(UUID imageId) throws Exception {
        return thisJpaRespository.findById(imageId).orElseThrow(() -> new Exception("Image not found"));
    }

    @Override
    public List<Image> getAllImagesByGift(UUID giftId) throws Exception {
        return thisJpaRespository.findAllByGift(giftId);
    }
    
}
