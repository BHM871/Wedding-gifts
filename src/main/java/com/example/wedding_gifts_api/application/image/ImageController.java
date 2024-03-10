package com.example.wedding_gifts_api.application.image;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.wedding_gifts_api.adapters.security.TokenManagerAdapter;
import com.example.wedding_gifts_api.core.domain.exceptions.common.MyException;
import com.example.wedding_gifts_api.core.domain.exceptions.gift.GiftNotNullableException;
import com.example.wedding_gifts_api.core.domain.exceptions.image.ImageExecutionException;
import com.example.wedding_gifts_api.core.domain.exceptions.image.ImageNotNullableException;
import com.example.wedding_gifts_api.core.usecases.image.IImageController;
import com.example.wedding_gifts_api.core.usecases.image.IImageUseCase;
import com.example.wedding_gifts_api.infra.dtos.commun.Base64ResponseDTO;
import com.example.wedding_gifts_api.infra.dtos.commun.MessageDTO;
import com.example.wedding_gifts_api.infra.dtos.exception.ExceptionResponseDTO;
import com.example.wedding_gifts_api.infra.dtos.image.DeleteImagesDTO;
import com.example.wedding_gifts_api.infra.dtos.image.InsertImagesDTO;

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
    @Autowired
    private TokenManagerAdapter tokenManager;

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
            ImageExecutionException exception = new ImageExecutionException("Some error", e);
            exception.setPath("/image/base64");
            throw exception;
        }
    }

    @Override
    @PutMapping(value = "/insert/{account}/{gift}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
        summary = "Insert images on a gift",
        description = "Authentication is necessary. Send to image in base64 format. Limit image size is 5MB."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully", content = @Content(schema = @Schema(type = "object", implementation = MessageDTO.class))),
        @ApiResponse(responseCode = "400", description = "Some error in processable request", content = @Content(schema = @Schema(type = "object", implementation = ExceptionResponseDTO.class))),
        @ApiResponse(responseCode = "403", description = "Unauthorizated", content = @Content(schema = @Schema(type = "object", implementation = ExceptionResponseDTO.class))),
        @ApiResponse(responseCode = "404", description = "Gift or Account not found", content = @Content(schema = @Schema(type = "object", implementation = ExceptionResponseDTO.class))),
        @ApiResponse(responseCode = "406", description = "Some value is null", content = @Content(schema = @Schema(type = "object", implementation = ExceptionResponseDTO.class))),
        @ApiResponse(responseCode = "422", description = "Invalid param or invalid value in request body", content = @Content(schema = @Schema(type = "object", implementation = ExceptionResponseDTO.class)))
    })
    public ResponseEntity<MessageDTO> insert(
        @RequestHeader("Authorization") String token,
        @PathVariable UUID account,
        @PathVariable UUID gift,
        @RequestBody List<String> images
    ) throws Exception {
        try{
            tokenManager.validateSessionId(token, account);

            InsertImagesDTO insert = new InsertImagesDTO(gift, account, images);
            validData(insert);

            services.insertImages(insert);
            return ResponseEntity.ok(new MessageDTO("successfully"));
        } catch (MyException e){
            e.setPath("/image/insert");
            throw e;
        } catch (Exception e){
            ImageExecutionException exception = new ImageExecutionException("Some error", e);
            exception.setPath("/image/insert");
            throw exception;
        }
    }

    @Override
    @DeleteMapping(value = "/delete/{account}/{gift}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
        summary = "Delete images of a gift",
        description = "Authentication is necessary. Send to image UUID for delete"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully", content = @Content(schema = @Schema(type = "object", implementation = MessageDTO.class))),
        @ApiResponse(responseCode = "400", description = "Some error in processable request", content = @Content(schema = @Schema(type = "object", implementation = ExceptionResponseDTO.class))),
        @ApiResponse(responseCode = "403", description = "Unauthorizated", content = @Content(schema = @Schema(type = "object", implementation = ExceptionResponseDTO.class))),
        @ApiResponse(responseCode = "404", description = "Gift or Account not found", content = @Content(schema = @Schema(type = "object", implementation = ExceptionResponseDTO.class))),
        @ApiResponse(responseCode = "406", description = "Some value is null", content = @Content(schema = @Schema(type = "object", implementation = ExceptionResponseDTO.class))),
        @ApiResponse(responseCode = "422", description = "Invalid param or invalid value in request body", content = @Content(schema = @Schema(type = "object", implementation = ExceptionResponseDTO.class)))
    })
    public ResponseEntity<MessageDTO> delete(
        @RequestHeader("Authorization") String token,
        @PathVariable UUID account,
        @PathVariable UUID gift,
        @RequestBody List<UUID> imgs
    ) throws Exception {
        try{
            tokenManager.validateSessionId(token, account);

            DeleteImagesDTO images = new DeleteImagesDTO(account, gift, imgs);

            validData(images);
            
            services.deleteImage(images);
            return ResponseEntity.ok(new MessageDTO("successfully"));
        } catch (MyException e){
            e.setPath("/image/update");
            throw e;
        } catch (Exception e){
            ImageExecutionException exception = new ImageExecutionException("Some error", e);
            exception.setPath("/image/update");
            throw exception;
        }
    }

    private void validData(InsertImagesDTO data) throws Exception {
        String isNull = "%s is null";

        if(data.giftId() == null) throw new GiftNotNullableException(String.format(isNull, "giftId"));
        if(data.accountId() == null) throw new GiftNotNullableException(String.format(isNull, "account"));
        if(data.images() == null || data.images().isEmpty()) throw new ImageNotNullableException(String.format(isNull, "images").replace("is", "are"));

    }
    
    private void validData(DeleteImagesDTO data) throws Exception {
        String isNull = "%s is null";

        if(data.giftId() == null) throw new GiftNotNullableException(String.format(isNull, "giftId"));
        if(data.accountId() == null) throw new GiftNotNullableException(String.format(isNull, "account"));
        if(data.images() == null || data.images().isEmpty()) throw new ImageNotNullableException(String.format(isNull, "images").replace("is", "are"));

    }
}
