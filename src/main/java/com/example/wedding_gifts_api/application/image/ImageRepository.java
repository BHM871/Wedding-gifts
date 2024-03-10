package com.example.wedding_gifts_api.application.image;

import java.util.Set;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.example.wedding_gifts_api.application.gift.GiftRepository;
import com.example.wedding_gifts_api.core.domain.exceptions.common.MyException;
import com.example.wedding_gifts_api.core.domain.exceptions.image.ImageExecutionException;
import com.example.wedding_gifts_api.core.domain.exceptions.image.ImageNotFoundException;
import com.example.wedding_gifts_api.core.domain.model.Gift;
import com.example.wedding_gifts_api.core.domain.model.Image;
import com.example.wedding_gifts_api.core.usecases.image.IImageRepository;
import com.example.wedding_gifts_api.infra.dtos.image.SaveImageDTO;
import com.example.wedding_gifts_api.infra.jpa.JpaImageRespository;

@Repository
public class ImageRepository implements IImageRepository {

    @Autowired
    private JpaImageRespository jpaRespository;
    @Autowired
    private GiftRepository giftRepository;

    @Override
    public Image saveImage(SaveImageDTO image) throws Exception {
        try{
            Image newImage = new Image(image);
            Gift gift = giftRepository.getGiftById(image.giftId());

            newImage.setGift(gift);

            return jpaRespository.save(newImage);
        } catch(MyException e){
            throw e;
        } catch(Exception e){
            throw new ImageExecutionException("Image can't be saved", e);
        }
    }

    @Override
    public void deleteImages(Set<Image> images) throws Exception {
        try{
            if(images == null || images.isEmpty()){
                throw new ImageNotFoundException("Some image was not found");
            }

            jpaRespository.deleteAll(images);
        } catch(MyException e){
            throw e;
        } catch(Exception e){
            throw new ImageExecutionException("Image can't be deleted", e);
        }
    }

    @Override
    public void deleteAllByGift(UUID giftId) throws Exception {
        try{
            jpaRespository.deleteAllByGift(giftId);
        } catch(Exception e){
            throw new ImageExecutionException("Image can't be deleted", e);
        }
    }

    @Override
    public Image getById(UUID imageId) throws Exception {
        return jpaRespository.findById(imageId).orElseThrow(() -> new ImageNotFoundException("Image not exists"));
    }

    @Override
    public Set<Image> getAllImagesByGift(UUID giftId) {
        return Set.copyOf(jpaRespository.findAllByGift(giftId));
    }
    
}
