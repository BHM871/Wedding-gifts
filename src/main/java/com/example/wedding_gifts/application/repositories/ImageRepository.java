package com.example.wedding_gifts.application.repositories;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.example.wedding_gifts.core.domain.dtos.image.DeleteImageDTO;
import com.example.wedding_gifts.core.domain.dtos.image.SaveImageDTO;
import com.example.wedding_gifts.core.domain.exceptions.common.MyException;
import com.example.wedding_gifts.core.domain.exceptions.gift.GiftNotYourException;
import com.example.wedding_gifts.core.domain.exceptions.image.ImageExecutionException;
import com.example.wedding_gifts.core.domain.exceptions.image.ImageNotFoundException;
import com.example.wedding_gifts.core.domain.exceptions.image.ImageNotYourException;
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
        try{
            Image newImage = new Image(image);
            Gift gift = giftRepository.getGiftById(image.giftId());

            newImage.setGift(gift);

            return thisJpaRespository.save(newImage);
        } catch(MyException e){
            throw e;
        } catch(Exception e){
            throw new ImageExecutionException("Image can't be saved", e);
        }
    }

    @Override
    public void deleteImage(DeleteImageDTO deleteImage) throws Exception {
        try{
            Image image = getById(deleteImage.imageId());
            
            int compared = image.getGift().getId()
                                .compareTo(deleteImage.giftId());
            if(compared != 0) throw new ImageNotYourException("This image is not this gift");
            
            compared = image.getGift().getAccount().getId()
                                .compareTo(deleteImage.accountId());
            if(compared != 0) throw new GiftNotYourException("This gift is not your");

            thisJpaRespository.delete(image);
        } catch(MyException e){
            throw e;
        } catch(Exception e){
            throw new ImageExecutionException("Image can't be deleted", e);
        }
    }

    @Override
    public void deleteAllByGift(UUID giftId) throws Exception {
        try{
            giftRepository.getGiftById(giftId);
            
            List<Image> images = getAllImagesByGift(giftId);

            if(images != null && !images.isEmpty()) thisJpaRespository.deleteAll(images);
        } catch(MyException e){
            throw e;
        } catch(Exception e){
            throw new ImageExecutionException("Image can't be deleted", e);
        }
    }

    @Override
    public Image getById(UUID imageId) throws Exception {
        return thisJpaRespository.findById(imageId).orElseThrow(() -> new ImageNotFoundException("Image not exists"));
    }

    @Override
    public List<Image> getAllImagesByGift(UUID giftId) {
        return thisJpaRespository.findAllByGift(giftId);
    }
    
}
