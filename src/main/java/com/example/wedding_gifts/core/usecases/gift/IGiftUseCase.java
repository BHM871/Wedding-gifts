package com.example.wedding_gifts.core.usecases.gift;

import java.util.List;
import java.util.UUID;

import com.example.wedding_gifts.core.domain.dtos.gift.CreateGiftDTO;
import com.example.wedding_gifts.core.domain.dtos.gift.DeleteGiftDTO;
import com.example.wedding_gifts.core.domain.dtos.gift.GiftResponseDTO;
import com.example.wedding_gifts.core.domain.dtos.gift.UpdateGiftDTO;
import com.example.wedding_gifts.core.domain.dtos.gift.searchers.SearcherDTO;
import com.example.wedding_gifts.core.domain.dtos.image.AddImagesDTO;
import com.example.wedding_gifts.core.domain.dtos.image.RemoveImagesDTO;

public interface IGiftUseCase {

    public GiftResponseDTO createGift(CreateGiftDTO gift) throws Exception;

    public void updateGift(UpdateGiftDTO gift) throws Exception;

    public void updateGift(AddImagesDTO images) throws Exception;

    public void updateGift(RemoveImagesDTO images) throws Exception;

    public void deleteGift(DeleteGiftDTO ids) throws Exception;

    public List<GiftResponseDTO> getAllGifts(UUID accountId) throws Exception;

    public List<GiftResponseDTO> getWithFilter(SearcherDTO searcher, UUID accoountId) throws Exception;
    
}
