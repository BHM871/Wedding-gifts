package com.example.wedding_gifts.core.usecases.gift;

import java.util.List;

import com.example.wedding_gifts.core.domain.dtos.gift.CreateGiftDTO;
import com.example.wedding_gifts.core.domain.dtos.gift.SearcherDTO;
import com.example.wedding_gifts.core.domain.dtos.gift.UpdateGiftDTO;
import com.example.wedding_gifts.core.domain.model.Gift;

public interface GiftUseCase {

    public Gift createGift(CreateGiftDTO gift) throws Exception;

    public void updateGift(UpdateGiftDTO gift) throws Exception;

    public List<Gift> getAllGifts();

    public List<Gift> getWithFilter(SearcherDTO searcher);
    
}
