package com.example.wedding_gifts.application.services;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.wedding_gifts.core.domain.dtos.image.DeleteImageDTO;
import com.example.wedding_gifts.core.domain.dtos.image.ImageDTO;
import com.example.wedding_gifts.core.domain.dtos.image.SaveImageDTO;
import com.example.wedding_gifts.core.domain.model.Image;
import com.example.wedding_gifts.core.usecases.image.IImageRepository;
import com.example.wedding_gifts.core.usecases.image.IImageUseCase;

@Service
public class ImageServices implements IImageUseCase {

    String sourceImages = "com/exemple/wedding_gifts/infra/images/";
    
    @Autowired
    IImageRepository repository;

    @Override
    public String saveImage(ImageDTO image) throws Exception {
        try {
            byte[] bytesOfImage = image.image().getBytes();
            Path path = Paths.get(sourceImages+image.accountId()+"/"+image.giftId()+"/"+LocalDateTime.now().toString());
            Files.write(path, bytesOfImage);

            return repository.saveImage(new SaveImageDTO(path.toString(), image.giftId())); 
        } catch (Exception e) {
            throw new Exception("Error in save image", e);
        }
    }

    @Override
    public void deleteImage(DeleteImageDTO deleteImage) throws Exception {
        boolean isDeleted = Files.deleteIfExists(Paths.get(deleteImage.pathImage()));
        
        if(isDeleted){
            repository.deleteImage(deleteImage);
        } else {
            throw new Exception("Image not exists");
        }
    }

    @Override
    public String getById(UUID imageId) throws Exception {
        return repository.getById(imageId).getPathImage();
    }

    @Override
    public UUID getImageIdByPath(String path) throws Exception {
        return repository.getByPath(path).getId();
    }

    @Override
    public List<String> getAllByGift(UUID giftId) throws Exception {
        List<Image> images = repository.getAllImagesByGift(giftId);

        List<String> pathImages = images.stream().map(image -> image.getPathImage()).toList();

        return pathImages;
    }
    
}