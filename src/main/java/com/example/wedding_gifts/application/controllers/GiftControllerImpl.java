package com.example.wedding_gifts.application.controllers;

import java.math.BigDecimal;
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
import org.springframework.web.bind.annotation.RestController;

import com.example.wedding_gifts.core.domain.dtos.gift.CreateGiftDTO;
import com.example.wedding_gifts.core.domain.dtos.gift.UpdateGiftDTO;
import com.example.wedding_gifts.core.domain.dtos.gift.searchers.SearcherDTO;
import com.example.wedding_gifts.core.domain.model.Gift;
import com.example.wedding_gifts.core.usecases.gift.GiftController;
import com.example.wedding_gifts.core.usecases.gift.GiftUseCase;

@RestController
@RequestMapping("/gift")
public class GiftControllerImpl implements GiftController {

    @Autowired
    GiftUseCase services;

    @Override
    @PostMapping("/create")
    public ResponseEntity<Gift> createGift(
        @RequestBody CreateGiftDTO gift
    ) throws Exception {
        validData(gift);
        return ResponseEntity.status(HttpStatus.CREATED).body(services.createGift(gift));
    }

    @Override
    @PutMapping("/update{id}")
    public ResponseEntity<String> updateGift(
        @RequestBody UpdateGiftDTO gift, 
        @RequestParam(name = "id", required = true) UUID id
    ) throws Exception {
        services.updateGift(gift, id);
        return ResponseEntity.ok("sussefully");
    }

    @Override
    @DeleteMapping("/delete{id}")
    public ResponseEntity<String> deleteGift(
        @RequestParam(name = "id", required = true) UUID id
    ) throws Exception {
        services.deleteGift(id);
        return ResponseEntity.ok("sussefully");
    }

    @Override
    @GetMapping("/")
    public ResponseEntity<List<Gift>> getAllGifts() {
        return ResponseEntity.ok(services.getAllGifts());
    }

    @Override
    @GetMapping("/filter")
    public ResponseEntity<List<Gift>> getWithFilter(
        @RequestBody SearcherDTO searcher
    ) throws Exception {
        return ResponseEntity.ok(services.getWithFilter(searcher));
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

}
