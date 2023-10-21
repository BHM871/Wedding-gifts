package com.example.wedding_gifts.application.services;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;

import javax.imageio.ImageIO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.drew.imaging.ImageMetadataReader;
import com.drew.metadata.Metadata;
import com.drew.metadata.exif.ExifIFD0Directory;
import com.example.wedding_gifts.core.domain.dtos.image.DeleteImageDTO;
import com.example.wedding_gifts.core.domain.dtos.image.ImageDTO;
import com.example.wedding_gifts.core.domain.dtos.image.SaveImageDTO;
import com.example.wedding_gifts.core.domain.exceptions.image.ImageNotFoundException;
import com.example.wedding_gifts.core.domain.model.Image;
import com.example.wedding_gifts.core.usecases.image.IImageRepository;
import com.example.wedding_gifts.core.usecases.image.IImageUseCase;

@Service
public class ImageServices implements IImageUseCase {

    private static final String sourceImages = "src/main/resources/db/images/";
    
    @Autowired
    private IImageRepository repository;

    @Override
    public Image createImage(ImageDTO image) throws Exception {
        try{
            if(
                image.image().getContentType() == null || 
                (!image.image().getContentType().endsWith("jpeg") && !image.image().getContentType().endsWith("png"))
            ) throw new Exception(image.image().getOriginalFilename() + " is not valid. Only JPEG or PNG");

            String extention = image.image().getContentType().replace("image/", "");

            Path path = generateImagePath(image, extention);

            cropImageAndSave(image.image().getBytes(), extention, path);

            return repository.saveImage(new SaveImageDTO(path.toString().replace('\\', '/'), image.giftId()));
        } catch(Exception e){
            String extention = image.image().getContentType().replace("image/", "");

            Path path = generateImagePath(image, extention);

            Files.deleteIfExists(path);

            throw e;
        }
    }

    @Override
    public void cropImageAndSave(byte[] bytesOfImage, String extention, Path path) throws Exception {
        BufferedImage buffer = ImageIO.read(new ByteArrayInputStream(bytesOfImage));
    
        int width = buffer.getWidth();
        int height = buffer.getHeight();

        if(extention.equals("jpeg")) {
            Metadata m = ImageMetadataReader.readMetadata(new ByteArrayInputStream(bytesOfImage));
            ExifIFD0Directory e = m.getFirstDirectoryOfType(ExifIFD0Directory.class);
            int o = e.getInt(ExifIFD0Directory.TAG_ORIENTATION);

            buffer = buffer.getSubimage(width/2-height/2, 0, height, height);

            if(o == 6){
                BufferedImage rotateI = new BufferedImage(height, height, buffer.getType());
                Graphics2D graphics = rotateI.createGraphics();

                graphics.rotate(Math.toRadians(90), height/2, height/2);
                graphics.drawImage(buffer, null, 0, 0);

                buffer = rotateI;
            }
        } else if(extention.equals("png")) {
            buffer = width > height
            ? buffer.getSubimage(width/2-height/2, 0, height, height)
            : width < height
                ? buffer.getSubimage(0, height/2-width/2, width, width)
                : buffer;
        } else {
            throw new Exception("Image is not accepted");
        }
        
        ImageIO.write(buffer, extention, path.toFile());

    }

    @Override
    public void deleteImage(DeleteImageDTO deleteImage) throws Exception {
        Image image = getById(deleteImage.imageId());
        
        repository.deleteImage(deleteImage);
        
        boolean isDeleted = Files.deleteIfExists(Paths.get(image.getPathImage()));

        if(!isDeleted) {
            throw new ImageNotFoundException(image.getPathImage().replace(
                                                "src/main/resources/db/images/"+deleteImage.accountId()+"/"+deleteImage.giftId()+"/", 
                                    " ") 
                                + "not exists");       
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
            throw new ImageNotFoundException("Some image not exists", e);
        }
    }

    @Override
    public Image getById(UUID imageId) throws Exception {
        return repository.getById(imageId);
    }

    @Override
    public List<Image> getAllByGift(UUID giftId) {
        return repository.getAllImagesByGift(giftId);
    }

    private Path generateImagePath(ImageDTO image, String extention) throws Exception {
        Path path = Paths.get(sourceImages+image.accountId()+"/"+image.giftId());
        if(!Files.exists(path)) 
            Files.createDirectories(path);

        return Paths.get(
                    path.toString()+"/"+
                    LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss_SSSSSSSS")).toString()+
                    "."+extention
                );
    }
    
}