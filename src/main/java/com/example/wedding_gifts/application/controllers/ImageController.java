package com.example.wedding_gifts.application.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.wedding_gifts.core.domain.dtos.commun.Base64ResponseDTO;
import com.example.wedding_gifts.core.domain.dtos.commun.MessageDTO;
import com.example.wedding_gifts.core.domain.dtos.exception.ExceptionResponseDTO;
import com.example.wedding_gifts.core.domain.dtos.image.UpdateImageDTO;
import com.example.wedding_gifts.core.domain.exceptions.common.MyException;
import com.example.wedding_gifts.core.domain.exceptions.gift.GiftNotNullableException;
import com.example.wedding_gifts.core.domain.exceptions.image.ImageExecutionException;
import com.example.wedding_gifts.core.domain.exceptions.image.ImageNotNullableException;
import com.example.wedding_gifts.core.usecases.image.IImageController;
import com.example.wedding_gifts.core.usecases.image.IImageUseCase;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/image")
@Tag(name = "Image")
public class ImageController implements IImageController {

    @Autowired
    private IImageUseCase services;

    @Override
    @GetMapping(value = "/base64", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(
        summary = "Convert from MultipartFile to Base64",
        description = "This endpoint is slow, so prefer to do this conversion yourself"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully", content = @Content(schema = @Schema(type = "object", implementation = Base64ResponseDTO.class))),
        @ApiResponse(responseCode = "400", description = "Some error in processable request", content = @Content(schema = @Schema(type = "object", implementation = ExceptionResponseDTO.class))),
    })
    public ResponseEntity<Base64ResponseDTO> toBase64(
        @RequestPart(value = "image") MultipartFile imageFile
    ) throws Exception {
        try{
            Base64ResponseDTO imagesReponse = new Base64ResponseDTO(services.toBase64(imageFile));

            return ResponseEntity.status(HttpStatus.OK).body(imagesReponse);
        } catch (MyException e){
            e.setPath("/image/base64");
            throw e;
        } catch (Exception e){
            ImageExecutionException exception = new ImageExecutionException("Some error");
            exception.setPath("/image/base64");
            throw exception;
        }
    }

    @Override
    @PutMapping(value = "/update", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
        summary = "Update image of a gift",
        description = "Authentication is necessary. 'imagesId' and 'images' can be null, but, not at the same time. The images in 'imagesId' will be deleted and 'images' will be added."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully", content = @Content(schema = @Schema(type = "object", implementation = MessageDTO.class))),
        @ApiResponse(responseCode = "400", description = "Some error in processable request", content = @Content(schema = @Schema(type = "object", implementation = ExceptionResponseDTO.class))),
        @ApiResponse(responseCode = "403", description = "Unauthorizated", content = @Content(schema = @Schema(type = "object", implementation = ExceptionResponseDTO.class))),
        @ApiResponse(responseCode = "404", description = "Gift or Account not found", content = @Content(schema = @Schema(type = "object", implementation = ExceptionResponseDTO.class))),
        @ApiResponse(responseCode = "406", description = "Some value is null", content = @Content(schema = @Schema(type = "object", implementation = ExceptionResponseDTO.class))),
        @ApiResponse(responseCode = "422", description = "Invalid param or invalid value in request body", content = @Content(schema = @Schema(type = "object", implementation = ExceptionResponseDTO.class)))
    })
    public ResponseEntity<MessageDTO> updateImages(
        @RequestBody UpdateImageDTO update
    ) throws Exception {
        try{
            validData(update);

            services.updateImages(update);
            return ResponseEntity.ok(new MessageDTO("successfully"));
        } catch (MyException e){
            e.setPath("/image/update");
            throw e;
        } catch (Exception e){
            ImageExecutionException exception = new ImageExecutionException("Some error");
            exception.setPath("/image/update");
            throw exception;
        }
    }

    private void validData(UpdateImageDTO data) throws Exception {
        String isNull = "%s is null";

        if(data.giftId() == null) throw new GiftNotNullableException(String.format(isNull, "giftId"));
        if(data.accountId() == null) throw new GiftNotNullableException(String.format(isNull, "account"));
        if(data.imagesId() == null && data.images() == null) throw new ImageNotNullableException(String.format(isNull, "images").replace("is", "are"));

    }
    
}
