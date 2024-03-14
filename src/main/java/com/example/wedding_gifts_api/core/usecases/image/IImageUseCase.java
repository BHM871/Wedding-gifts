package com.example.wedding_gifts_api.core.usecases.image;

import java.awt.image.BufferedImage;
import java.nio.file.Path;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.springframework.web.multipart.MultipartFile;

import com.example.wedding_gifts_api.core.domain.model.Image;
import com.example.wedding_gifts_api.infra.dtos.image.ImageDTO;

public interface IImageUseCase {
    
    public Image createImage(ImageDTO saveImage) throws Exception;

    public void cropImageAndSave(byte[] imageBytes, String extention, Path path) throws Exception;

    public void insertImages(UUID account, UUID gift, List<String> images) throws Exception;

    public void deleteImage(UUID account, UUID gift, List<UUID> images) throws Exception;

    public void deleteAllByGift(UUID giftId) throws Exception;

    public Image getById(UUID imageId) throws Exception;

    public Set<Image> getAllByGift(UUID giftId);

    public String toBase64(MultipartFile image) throws Exception;

    public String toBase64(BufferedImage image) throws Exception;

    public BufferedImage toImage(String base64) throws Exception;

    public byte[] toBytes(BufferedImage image) throws Exception;

}
