package com.example.wedding_gifts_api.core.usecases.gift;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.example.wedding_gifts_api.core.domain.model.Gift;
import com.example.wedding_gifts_api.infra.dtos.gift.CreateGiftDTO;
import com.example.wedding_gifts_api.infra.dtos.gift.GiftResponseDTO;
import com.example.wedding_gifts_api.infra.dtos.gift.UpdateGiftDTO;
import com.example.wedding_gifts_api.infra.dtos.gift.searchers.SearcherDTO;

public interface IGiftUseCase {

    public GiftResponseDTO createGift(UUID accountId, CreateGiftDTO gift) throws Exception;

    public void updateGift(UUID accountId, UUID giftId, UpdateGiftDTO gift) throws Exception;

    public void deleteGift(UUID accountId, UUID giftId) throws Exception;

    public void deleteAllByAccount(UUID accountId) throws Exception;

    public Gift getById(UUID giftId) throws Exception;

    public Page<GiftResponseDTO> getAllGifts(UUID accountId, Pageable page) throws Exception;

    public Page<GiftResponseDTO> getWithFilter(SearcherDTO searcher, UUID accoountId, Pageable page) throws Exception;
    
}
