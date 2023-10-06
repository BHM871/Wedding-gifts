package com.example.wedding_gifts.application.services;

import java.io.IOException;
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
    IImageRepository repository;

    @Override
    public Image saveImage(ImageDTO image) throws Exception {
        try {
            byte[] bytesOfImage = image.image().getBytes();

            Path path = Paths.get(sourceImages+image.accountId()+"/"+image.giftId());
            Files.createDirectories(path);

            Path imagePath = Paths.get(generateImagePath(path, image.image())); 
            Files.write(imagePath, bytesOfImage);

            return repository.saveImage(new SaveImageDTO(imagePath.toString().replace('\\', '/'), image.giftId())); 
        } catch (IOException e) {
            throw new Exception(e.getMessage(), e);
        }
    }

    @Override
    public void deleteImage(DeleteImageDTO deleteImage) throws Exception {
        Image image = repository.getById(deleteImage.imageId());
        boolean isDeleted = Files.deleteIfExists(Paths.get(image.getPathImage()));
        
        if(isDeleted){
            repository.deleteImage(deleteImage);
        } else {
            throw new Exception("Image not exists");
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
                LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss")).toString()+"."
                +image.getContentType().replaceAll("image/", "");
    }
    
}