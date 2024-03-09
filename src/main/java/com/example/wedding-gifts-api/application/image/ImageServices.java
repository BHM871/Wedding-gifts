package com.example.wedding_gifts.application.image;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Base64;
import java.util.Collections;
import java.util.Set;
import java.util.UUID;

import javax.imageio.ImageIO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.drew.imaging.ImageMetadataReader;
import com.drew.metadata.Metadata;
import com.drew.metadata.exif.ExifIFD0Directory;
import com.example.wedding_gifts.common.MyZone;
import com.example.wedding_gifts.core.domain.exceptions.common.MyException;
import com.example.wedding_gifts.core.domain.exceptions.gift.GiftNotYourException;
import com.example.wedding_gifts.core.domain.exceptions.image.ImageExecutionException;
import com.example.wedding_gifts.core.domain.exceptions.image.ImageInvalidValueException;
import com.example.wedding_gifts.core.domain.exceptions.image.ImageNotFoundException;
import com.example.wedding_gifts.core.domain.exceptions.image.ImageNotYourException;
import com.example.wedding_gifts.core.domain.model.Image;
import com.example.wedding_gifts.core.usecases.image.IImageRepository;
import com.example.wedding_gifts.core.usecases.image.IImageUseCase;
import com.example.wedding_gifts.infra.dtos.image.DeleteImagesDTO;
import com.example.wedding_gifts.infra.dtos.image.ImageDTO;
import com.example.wedding_gifts.infra.dtos.image.InsertImagesDTO;
import com.example.wedding_gifts.infra.dtos.image.SaveImageDTO;

@Service
@SuppressWarnings("null")
public class ImageServices implements IImageUseCase {

    private static final String sourceImages = "src/main/resources/db/images/";
    
    @Autowired
    private IImageRepository repository;

    @Override
    public Image createImage(ImageDTO image) throws Exception {
        String imageBase64 = "";
        String imageData = "";

        try{
            imageBase64 = toBase64(image.image());

            if(
                imageData == null || 
                (!imageData.endsWith("jpeg") && !imageData.endsWith("png"))
            ) throw new ImageInvalidValueException("Image is not valid. Only JPEG or PNG");

            String extention = imageData.replace("data:image/", "");

            Path path = generateImagePath(image, extention);

            cropImageAndSave(toBytes(image.image()), extention, path);

            return repository.saveImage(new SaveImageDTO(path.toString().replace('\\', '/'), image.giftId()));
        } catch(MyException e){
            throw e;        
        } catch(Exception e){
            imageBase64 = toBase64(image.image());
            imageData = imageBase64.split(";")[0];

            String extention = imageData.replace("data:image/", "");

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
            throw new ImageInvalidValueException("Image is not accepted");
        }
        
        ImageIO.write(buffer, extention, path.toFile());
    }

    @Override
    public void insertImages(InsertImagesDTO insert) throws Exception {
        for(String image : insert.images()) {
            createImage(
                new ImageDTO(toImage(image), insert.giftId(), insert.accountId())
            );
        }
    }

    @Override
    public void deleteImage(DeleteImagesDTO deleteImages) throws Exception {
        try{
            if(deleteImages.images().isEmpty()) return;

            Set<Image> images = Collections.emptySet();

            for(UUID imgId : deleteImages.images()){
                Image image = getById(imgId);

                
                if(!Files.exists(Path.of(image.getPathImage()))){
                    repository.deleteImages(Collections.singleton(image));

                    throw new ImageNotFoundException(String.format("This %s image ID not exists", image.getId())); 
                }

                int compared = image.getGift().getId().compareTo(deleteImages.giftId());
                if(compared != 0) throw new ImageNotYourException(String.format("This %s is not this gift", image.getId()));
                
                compared = image.getGift().getAccount().getId().compareTo(deleteImages.accountId());
                if(compared != 0) throw new GiftNotYourException("Image of a gift that is not your");

                images.add(image);
            }

            repository.deleteImages(images);
        } catch(Exception e){
            throw new ImageExecutionException("Some error in delete images");
        }
    }

    @Override
    public void deleteAllByGift(UUID giftId) throws Exception {
        try{
            Set<Image> images = getAllByGift(giftId);

            if(images.isEmpty()) return;

            for(Image image : images){
                if(!Files.exists(Path.of(image.getPathImage()))){
                    repository.deleteImages(Collections.singleton(image));

                    throw new ImageNotFoundException(String.format("This %s image ID not exists", image.getId())); 
                }
            }
        
            repository.deleteAllByGift(giftId);
        } catch (Exception e) {
            throw new ImageNotFoundException("Some image not exists", e);
        }
    }

    @Override
    public Image getById(UUID imageId) throws Exception {
        return repository.getById(imageId);
    }

    @Override
    public Set<Image> getAllByGift(UUID giftId) {
        return repository.getAllImagesByGift(giftId);
    }

    @Override
    public String toBase64(MultipartFile image) throws Exception {
        try{
            if(
                image.getContentType() == null ||
                !image.getContentType().contains("image/") || 
                (!image.getContentType().endsWith("jpeg") && !image.getContentType().endsWith("png"))
            ){
                throw new ImageInvalidValueException(image.getOriginalFilename() + " is not a image");
            }

            return "data:" + image.getContentType() + ";base64," + Base64.getEncoder().encodeToString(image.getBytes());
        } catch (MyException e){
            throw e; 
        } catch (Exception e){
            throw new ImageExecutionException("Image can't be conveted", e);
        }
    }

    @Override
    public String toBase64(BufferedImage image) throws Exception {
        try{
            byte[] bytes = toBytes(image);

            return "data:" + getMIMEType(image) + ";base64," + Base64.getEncoder().encodeToString(bytes);
        } catch (Exception e){
            throw new ImageExecutionException("Imge can't be converted", e);
        }
    }

    @Override
    public BufferedImage toImage(String base64) throws Exception {
        try{
            base64 = base64.contains("data:") ? base64.split(",")[1] : base64;

            byte[] bytes = Base64.getDecoder().decode(base64);
            
            ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
            return ImageIO.read(bais);
        } catch (Exception e){
            throw new ImageExecutionException("Image can't be conveted", e);
        }
    }

    @Override
    public byte[] toBytes(BufferedImage image) throws Exception {
        try{
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(image, getMIMEType(image).replace("image/", ""), baos);
            
            return baos.toByteArray();
        } catch (Exception e){
            throw new ImageExecutionException("Imge can't be converted", e);
        }
    }

    private Path generateImagePath(ImageDTO image, String extention) throws Exception {
        try{
            Path path = Paths.get(sourceImages+image.accountId()+"/"+image.giftId());
            if(!Files.exists(path)) 
                Files.createDirectories(path);

            return Paths.get(
                        path.toString()+"/"+
                        LocalDateTime.now(MyZone.zoneId()).format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss_SSSSSSSS")).toString()+
                        "."+extention
                    );
        } catch (IOException e){
            throw new ImageExecutionException("Some error in generated path", e);
        }
    }

    private String getMIMEType(BufferedImage image){
        int type = image.getType();

        switch (type) {
            case BufferedImage.TYPE_3BYTE_BGR:
                return "image/jpeg";
            case BufferedImage.TYPE_4BYTE_ABGR:
                return "image/png";
            default:
                return "image/png";
        }
    }
    
}