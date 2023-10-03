package com.example.wedding_gifts.core.usecases.image;

import com.example.wedding_gifts.core.domain.dtos.image.DeleteImageDTO;
import com.example.wedding_gifts.core.domain.dtos.image.SaveImageDTO;
import com.example.wedding_gifts.core.domain.dtos.image.UpdateImageDTO;

public interface IImageUseCase {
    
    public String saveImage(SaveImageDTO saveImage) throws Exception;

    public String updateImage(UpdateImageDTO updateImage) throws Exception;

    public void deleteImage(DeleteImageDTO deleteImage) throws Exception;

}
