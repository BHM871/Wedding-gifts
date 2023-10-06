package com.example.wedding_gifts.core.usecases.gift;

import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import com.example.wedding_gifts.core.domain.dtos.commun.MessageDTO;
import com.example.wedding_gifts.core.domain.dtos.gift.CreateGiftDTO;
import com.example.wedding_gifts.core.domain.dtos.gift.DeleteGiftDTO;
import com.example.wedding_gifts.core.domain.dtos.gift.GiftResponseDTO;
import com.example.wedding_gifts.core.domain.dtos.gift.UpdateGiftDTO;
import com.example.wedding_gifts.core.domain.dtos.gift.searchers.SearcherDTO;

public interface IGiftController {
    
    public ResponseEntity<GiftResponseDTO> createGift(CreateGiftDTO gift) throws Exception;

    public ResponseEntity<MessageDTO> updateGift(UpdateGiftDTO gift) throws Exception;

    public ResponseEntity<MessageDTO> deleteGift(DeleteGiftDTO ids) throws Exception;

    public ResponseEntity<List<GiftResponseDTO>> getAllGifts(UUID accountId) throws Exception;

    public ResponseEntity<List<GiftResponseDTO>> getWithFilter(SearcherDTO searcher, UUID accountId) throws Exception;

}
