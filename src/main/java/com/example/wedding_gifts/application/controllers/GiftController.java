package com.example.wedding_gifts.application.controllers;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
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

import com.example.wedding_gifts.core.domain.dtos.commun.MessageDTO;
import com.example.wedding_gifts.core.domain.dtos.gift.CreateGiftDTO;
import com.example.wedding_gifts.core.domain.dtos.gift.DeleteGiftDTO;
import com.example.wedding_gifts.core.domain.dtos.gift.GiftResponseDTO;
import com.example.wedding_gifts.core.domain.dtos.gift.UpdateGiftDTO;
import com.example.wedding_gifts.core.domain.dtos.gift.searchers.SearcherDTO;
import com.example.wedding_gifts.core.domain.dtos.image.UpdateImageDTO;
import com.example.wedding_gifts.core.usecases.gift.IGiftController;
import com.example.wedding_gifts.core.usecases.gift.IGiftUseCase;

@RestController
@RequestMapping("/gift")
@CrossOrigin
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
        return ResponseEntity.ok(new MessageDTO("sussefully"));
    }

    @Override
    @PutMapping("/update/image")
    public ResponseEntity<MessageDTO> updateGift(
        @RequestPart(name = "update") UpdateImageDTO update,
        @RequestPart(required = false) MultipartFile images[]
    ) throws Exception {
        validData(update, images);

        services.updateGift(update, images);
        return ResponseEntity.ok(new MessageDTO("sussefully"));
    }

    @Override
    @DeleteMapping("/delete")
    public ResponseEntity<MessageDTO> deleteGift(
        @RequestBody DeleteGiftDTO ids
    ) throws Exception {
        validData(ids);

        services.deleteGift(ids);
        return ResponseEntity.ok(new MessageDTO("sussefully"));
    }

    @Override
    @DeleteMapping("/delete/all{account}")
    public ResponseEntity<MessageDTO> deleteAllByAccount(
        @RequestParam(name = "account") UUID accountId
    ) throws Exception {
        if(accountId == null) throw new Exception("Account id is null");

        services.deleteAllByAccount(accountId);
        return ResponseEntity.ok(new MessageDTO("sussefully"));
    }

    @Override
    @GetMapping("/all{account}")
    public ResponseEntity<List<GiftResponseDTO>> getAllGifts(
        @RequestParam(name = "account") UUID accountId
    ) throws Exception {
        return ResponseEntity.ok(services.getAllGifts(accountId));
    }

    @Override
    @GetMapping("/filter{account}")
    public ResponseEntity<List<GiftResponseDTO>> getWithFilter(
        @RequestBody SearcherDTO searcher,
        @RequestParam(name = "account") UUID accountId
    ) throws Exception {
        return ResponseEntity.ok(services.getWithFilter(searcher, accountId));
    }
    
    private void validData(CreateGiftDTO data) throws Exception {
        String invalid = "Some value is invalid";
        String isNull = "Some value is null";
        
        if(data.title() == null || data.title().isEmpty()) throw new Exception(isNull);
        if(data.price() == null) throw new Exception(isNull);
        if(data.categories() == null || data.categories().isEmpty()) throw new Exception(isNull);

        if(data.title().length() < 3) throw new Exception(invalid);
        if(data.giftDescription() != null && data.giftDescription().length() < 5) throw new Exception(invalid);
        if(data.price().doubleValue() < BigDecimal.TEN.doubleValue()) throw new Exception(invalid);

    }

    private void validData(UpdateGiftDTO data) throws Exception {
        String isNull = "Some value is null";

        if(data.giftId() == null) throw new Exception(isNull);
        if(data.accountId() == null) throw new Exception(isNull);

    }

    private void validData(UpdateImageDTO data, MultipartFile images[]) throws Exception {
        String isNull = "Some value is null";

        if(data.giftId() == null) throw new Exception(isNull);
        if(data.accountId() == null) throw new Exception(isNull);
        if(data.imagesId() == null && images == null) throw new Exception(isNull);

    }

    private void validData(DeleteGiftDTO data) throws Exception {
        String isNull = "Some value is null";

        if(data.giftId() == null) throw new Exception(isNull);
        if(data.accountId() == null) throw new Exception(isNull);

    }

}
