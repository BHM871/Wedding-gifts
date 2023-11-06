package com.example.wedding_gifts.core.usecases.gift;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.example.wedding_gifts.core.domain.dtos.gift.CreateGiftDTO;
import com.example.wedding_gifts.core.domain.dtos.gift.DeleteGiftDTO;
import com.example.wedding_gifts.core.domain.dtos.gift.GiftResponseDTO;
import com.example.wedding_gifts.core.domain.dtos.gift.UpdateGiftDTO;
import com.example.wedding_gifts.core.domain.dtos.gift.searchers.SearcherDTO;
import com.example.wedding_gifts.core.domain.model.Gift;

public interface IGiftUseCase {

    public GiftResponseDTO createGift(CreateGiftDTO gift) throws Exception;

    public void updateGift(UpdateGiftDTO gift) throws Exception;

    public void deleteGift(DeleteGiftDTO ids) throws Exception;

    public void deleteAllByAccount(UUID accountId) throws Exception;

    public Gift getById(UUID giftId) throws Exception;

    public Page<GiftResponseDTO> getAllGifts(UUID accountId, Pageable page) throws Exception;

    public Page<GiftResponseDTO> getWithFilter(SearcherDTO searcher, UUID accoountId, Pageable page) throws Exception;
    
}
