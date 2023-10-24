package com.example.wedding_gifts.application.controllers;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.wedding_gifts.common.Validation;
import com.example.wedding_gifts.core.domain.dtos.commun.MessageDTO;
import com.example.wedding_gifts.core.domain.dtos.gift.CreateGiftDTO;
import com.example.wedding_gifts.core.domain.dtos.gift.DeleteGiftDTO;
import com.example.wedding_gifts.core.domain.dtos.gift.GiftResponseDTO;
import com.example.wedding_gifts.core.domain.dtos.gift.UpdateGiftDTO;
import com.example.wedding_gifts.core.domain.dtos.gift.searchers.SearcherDTO;
import com.example.wedding_gifts.core.domain.dtos.image.UpdateImageDTO;
import com.example.wedding_gifts.core.domain.exceptions.gift.GiftInvalidValueException;
import com.example.wedding_gifts.core.domain.exceptions.gift.GiftNotNullableException;
import com.example.wedding_gifts.core.domain.exceptions.image.ImageNotNullableException;
import com.example.wedding_gifts.core.usecases.gift.IGiftController;
import com.example.wedding_gifts.core.usecases.gift.IGiftUseCase;

@RestController
@RequestMapping("/gift")
public class GiftController implements IGiftController {

    @Autowired
    private IGiftUseCase services;

    @Override
    @PostMapping("/create")
    public ResponseEntity<GiftResponseDTO> createGift(
        @RequestPart CreateGiftDTO gift,
        @RequestPart(required = false) MultipartFile images[]
    ) throws Exception {
        validData(gift);

        return ResponseEntity.status(HttpStatus.CREATED).body(services.createGift(gift, images));
    }

    @Override
    @PutMapping("/update")
    public ResponseEntity<MessageDTO> updateGift(
        @RequestBody UpdateGiftDTO gift
    ) throws Exception {
        validData(gift);

        services.updateGift(gift);
        return ResponseEntity.ok(new MessageDTO("successfully"));
    }

    @Override
    @PutMapping("/update/image")
    public ResponseEntity<MessageDTO> updateGift(
        @RequestPart UpdateImageDTO update,
        @RequestPart(required = false) MultipartFile images[]
    ) throws Exception {
        validData(update, images);

        services.updateGift(update, images);
        return ResponseEntity.ok(new MessageDTO("successfully"));
    }

    @Override
    @DeleteMapping("/delete")
    public ResponseEntity<MessageDTO> deleteGift(
        @RequestBody DeleteGiftDTO ids
    ) throws Exception {
        validData(ids);

        services.deleteGift(ids);
        return ResponseEntity.ok(new MessageDTO("successfully"));
    }

    @Override
    @DeleteMapping("/delete/all{account}")
    public ResponseEntity<MessageDTO> deleteAllByAccount(
        @RequestParam UUID account
    ) throws Exception {
        if(account == null) throw new Exception("Account id is null");

        services.deleteAllByAccount(account);
        return ResponseEntity.ok(new MessageDTO("successfully"));
    }

    @Override
    @GetMapping("/all{account}")
    public ResponseEntity<List<GiftResponseDTO>> getAllGifts(
        @RequestParam UUID account
    ) throws Exception {
        return ResponseEntity.ok(services.getAllGifts(account));
    }

    @Override
    @GetMapping("/filter{account}")
    public ResponseEntity<List<GiftResponseDTO>> getWithFilter(
        @RequestBody SearcherDTO searcher,
        @RequestParam UUID account
    ) throws Exception {
        return ResponseEntity.ok(services.getWithFilter(searcher, account));
    }
    
    private void validData(CreateGiftDTO data) throws Exception {
        String invalid = "%s is invalid";
        String isNull = "%s is null";
        
        if(data.title() == null || data.title().isEmpty()) throw new GiftNotNullableException(String.format(isNull, "title"));
        if(data.price() == null) throw new GiftNotNullableException(String.format(isNull, "price"));
        if(data.categories() == null || data.categories().isEmpty()) throw new GiftNotNullableException(String.format(isNull, "categories"));

        if(!Validation.title(data.title())) throw new GiftInvalidValueException(String.format(invalid, "title"));
        if(!Validation.description(data.giftDescription())) throw new GiftInvalidValueException(String.format(invalid, "description"));
        if(!Validation.price(data.price())) throw new GiftInvalidValueException(String.format(invalid, "price"));

    }

    private void validData(UpdateGiftDTO data) throws Exception {
        String isNull = "%s is null";

        if(data.giftId() == null) throw new GiftNotNullableException(String.format(isNull, "giftId"));
        if(data.accountId() == null) throw new GiftNotNullableException(String.format(isNull, "account"));

    }

    private void validData(UpdateImageDTO data, MultipartFile images[]) throws Exception {
        String isNull = "%s is null";

        if(data.giftId() == null) throw new GiftNotNullableException(String.format(isNull, "giftId"));
        if(data.accountId() == null) throw new GiftNotNullableException(String.format(isNull, "account"));
        if(data.imagesId() == null && images == null) throw new ImageNotNullableException(String.format(isNull, "images").replace("is", "are"));

    }

    private void validData(DeleteGiftDTO data) throws Exception {
        String isNull = "%s is null";

        if(data.giftId() == null) throw new GiftNotNullableException(String.format(isNull, "giftId"));
        if(data.accountId() == null) throw new GiftNotNullableException(String.format(isNull, "account"));

    }

}
