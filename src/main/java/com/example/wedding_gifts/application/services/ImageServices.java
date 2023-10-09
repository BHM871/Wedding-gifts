package com.example.wedding_gifts.application.services;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.wedding_gifts.core.domain.dtos.image.DeleteImageDTO;
import com.example.wedding_gifts.core.domain.dtos.image.ImageDTO;
import com.example.wedding_gifts.core.domain.dtos.image.SaveImageDTO;
import com.example.wedding_gifts.core.domain.model.Image;
import com.example.wedding_gifts.core.usecases.image.IImageRepository;
import com.example.wedding_gifts.core.usecases.image.IImageUseCase;

@Service
public class ImageServices implements IImageUseCase {

    private static final String sourceImages = "src/main/resources/db/images/";
    
    @Autowired
    private IImageRepository repository;

    @Override
    public Image saveImage(ImageDTO image) throws Exception {

        if(
            image.image().getContentType() != null && 
            (image.image().getContentType().endsWith("gif") || !image.image().getContentType().startsWith("image"))
        ) throw new Exception(image.image().getOriginalFilename() + " is not a image");
    
        byte[] bytesOfImage = image.image().getBytes();

        Path path = Paths.get(sourceImages+image.accountId()+"/"+image.giftId());
        if(!Files.exists(path)) 
            Files.createDirectories(path);

        Path imagePath = Paths.get(generateImagePath(path, image.image())); 
        Files.write(imagePath, bytesOfImage);

        return repository.saveImage(new SaveImageDTO(imagePath.toString().replace('\\', '/'), image.giftId()));
    }

    @Override
    public void deleteImage(DeleteImageDTO deleteImage) throws Exception {
        Image image = getById(deleteImage.imageId());
        
        repository.deleteImage(deleteImage);
        
        boolean isDeleted = Files.deleteIfExists(Paths.get(image.getPathImage()));

        if(!isDeleted) {
            throw new Exception(sourceImages.replace(
                                                "src/main/resources/db/images/"+deleteImage.accountId()+"/"+deleteImage.giftId()+"/", 
                                    ""
                                            ) + " not exists");       
        }
    }

    @Override
    public void deleteAllByGift(UUID giftId) throws Exception {
        try{
            List<Image> images = getAllByGift(giftId);
        
            repository.deleteAllByGift(giftId);
        
            for(Image image : images) {
                Files.deleteIfExists(Paths.get(image.getPathImage()));
            }
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Override
    public Image getById(UUID imageId) throws Exception {
        return repository.getById(imageId);
    }

    @Override
    public List<Image> getAllByGift(UUID giftId) throws Exception {
        return repository.getAllImagesByGift(giftId);
    }

    private String generateImagePath(Path path, MultipartFile image) throws Exception {
        if(image.getContentType() == null) throw new Exception();

        return path.toString()+"/"+
                LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss_SSSSSSSS")).toString()+"."
                +image.getContentType().replaceAll("image/", "");
    }
    
}