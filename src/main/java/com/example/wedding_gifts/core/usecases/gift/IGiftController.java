package com.example.wedding_gifts.core.usecases.gift;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import com.example.wedding_gifts.infra.dtos.commun.MessageDTO;
import com.example.wedding_gifts.infra.dtos.gift.CreateGiftDTO;
import com.example.wedding_gifts.infra.dtos.gift.GiftResponseDTO;
import com.example.wedding_gifts.infra.dtos.gift.UpdateGiftDTO;
import com.example.wedding_gifts.infra.dtos.gift.searchers.SearcherDTO;

public interface IGiftController {
    
    public ResponseEntity<GiftResponseDTO> createGift(CreateGiftDTO gift) throws Exception;

    public ResponseEntity<MessageDTO> updateGift(UUID accountId, UUID giftId, UpdateGiftDTO gift) throws Exception;

    public ResponseEntity<MessageDTO> deleteGift(UUID accountId, UUID giftId) throws Exception;

    public ResponseEntity<MessageDTO> deleteAllByAccount(UUID accountId) throws Exception;

    public ResponseEntity<Page<GiftResponseDTO>> getAllGifts(UUID accountId, Pageable page) throws Exception;

    public ResponseEntity<Page<GiftResponseDTO>> getWithFilter(SearcherDTO searcher, UUID accountId, Pageable page) throws Exception;

}
