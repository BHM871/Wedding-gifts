package com.example.wedding_gifts.core.usecases.image;

import java.awt.image.BufferedImage;
import java.nio.file.Path;
import java.util.List;
import java.util.UUID;

import org.springframework.web.multipart.MultipartFile;

import com.example.wedding_gifts.core.domain.dtos.image.DeleteImageDTO;
import com.example.wedding_gifts.core.domain.dtos.image.ImageDTO;
import com.example.wedding_gifts.core.domain.dtos.image.UpdateImageDTO;
import com.example.wedding_gifts.core.domain.model.Image;

public interface IImageUseCase {
    
    public Image createImage(ImageDTO saveImage) throws Exception;

    public void cropImageAndSave(byte[] imageBytes, String extention, Path path) throws Exception;

    public void updateImages(UpdateImageDTO images) throws Exception;

    public void deleteImage(DeleteImageDTO deleteImage) throws Exception;

    public void deleteAllByGift(UUID giftId) throws Exception;

    public Image getById(UUID imageId) throws Exception;

    public List<Image> getAllByGift(UUID giftId);

    public String toBase64(MultipartFile image) throws Exception;

    public String toBase64(BufferedImage image) throws Exception;

    public BufferedImage toImage(String base64) throws Exception;

    public byte[] toBytes(BufferedImage image) throws Exception;

}
