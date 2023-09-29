package com.example.wedding_gifts.core.usecases.gift;

import java.util.List;
import java.util.UUID;

import com.example.wedding_gifts.core.domain.dtos.gift.CreateGiftDTO;
import com.example.wedding_gifts.core.domain.dtos.gift.UpdateGiftDTO;
import com.example.wedding_gifts.core.domain.dtos.gift.searchers.SearcherDTO;
import com.example.wedding_gifts.core.domain.model.Gift;

public interface GiftUseCase {

    public Gift createGift(CreateGiftDTO gift) throws Exception;

    public void updateGift(UpdateGiftDTO gift, UUID id) throws Exception;

    public void deleteGift(UUID id) throws Exception;

    public List<Gift> getAllGifts();

    public List<Gift> getWithFilter(SearcherDTO searcher) throws Exception;
    
}
