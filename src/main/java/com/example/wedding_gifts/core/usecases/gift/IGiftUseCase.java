package com.example.wedding_gifts.core.usecases.gift;

import java.util.List;
import java.util.UUID;

import org.springframework.web.multipart.MultipartFile;

import com.example.wedding_gifts.core.domain.dtos.gift.CreateGiftDTO;
import com.example.wedding_gifts.core.domain.dtos.gift.DeleteGiftDTO;
import com.example.wedding_gifts.core.domain.dtos.gift.GiftResponseDTO;
import com.example.wedding_gifts.core.domain.dtos.gift.UpdateGiftDTO;
import com.example.wedding_gifts.core.domain.dtos.gift.searchers.SearcherDTO;
import com.example.wedding_gifts.core.domain.dtos.image.UpdateImageDTO;

public interface IGiftUseCase {

    public GiftResponseDTO createGift(CreateGiftDTO gift, MultipartFile images[]) throws Exception;

    public void updateGift(UpdateGiftDTO gift) throws Exception;

    public void updateGift(UpdateImageDTO update, MultipartFile images[]) throws Exception;

    public void deleteGift(DeleteGiftDTO ids) throws Exception;

    public void deleteAllByAccount(UUID accountId) throws Exception;

    public List<GiftResponseDTO> getAllGifts(UUID accountId) throws Exception;

    public List<GiftResponseDTO> getWithFilter(SearcherDTO searcher, UUID accoountId) throws Exception;
    
}
