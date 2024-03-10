package com.example.wedding_gifts_api.core.usecases.gift;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import com.example.wedding_gifts_api.infra.dtos.commun.MessageDTO;
import com.example.wedding_gifts_api.infra.dtos.gift.CreateGiftDTO;
import com.example.wedding_gifts_api.infra.dtos.gift.GiftResponseDTO;
import com.example.wedding_gifts_api.infra.dtos.gift.UpdateGiftDTO;
import com.example.wedding_gifts_api.infra.dtos.gift.searchers.SearcherDTO;

public interface IGiftController {
    
    public ResponseEntity<GiftResponseDTO> createGift(String token, UUID account, CreateGiftDTO gift) throws Exception;

    public ResponseEntity<MessageDTO> updateGift(String token, UUID account, UUID gift, UpdateGiftDTO update) throws Exception;

    public ResponseEntity<MessageDTO> deleteGift(String token, UUID account, UUID gift) throws Exception;

    public ResponseEntity<MessageDTO> deleteAllByAccount(String token, UUID account) throws Exception;

    public ResponseEntity<Page<GiftResponseDTO>> getAllGifts(UUID account, Pageable page) throws Exception;

    public ResponseEntity<Page<GiftResponseDTO>> getWithFilter(SearcherDTO searcher, UUID account, Pageable page) throws Exception;

}
