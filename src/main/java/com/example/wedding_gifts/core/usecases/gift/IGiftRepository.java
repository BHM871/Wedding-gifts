package com.example.wedding_gifts.core.usecases.gift;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.example.wedding_gifts.core.domain.dtos.gift.CreateGiftDTO;
import com.example.wedding_gifts.core.domain.dtos.gift.DeleteGiftDTO;
import com.example.wedding_gifts.core.domain.dtos.gift.UpdateGiftDTO;
import com.example.wedding_gifts.core.domain.dtos.gift.searchers.SearcherByCategoriesAndPriceDTO;
import com.example.wedding_gifts.core.domain.dtos.gift.searchers.SearcherByCategoriesDTO;
import com.example.wedding_gifts.core.domain.dtos.gift.searchers.SearcherByPriceDTO;
import com.example.wedding_gifts.core.domain.dtos.gift.searchers.SearcherByTitleAndCategoriesDTO;
import com.example.wedding_gifts.core.domain.dtos.gift.searchers.SearcherByTitleAndPriceDTO;
import com.example.wedding_gifts.core.domain.dtos.gift.searchers.SearcherByTitleDTO;
import com.example.wedding_gifts.core.domain.dtos.gift.searchers.SearcherDTO;
import com.example.wedding_gifts.core.domain.model.Gift;

public interface IGiftRepository {
    
    public Gift save(Gift gift) throws Exception;

    public Gift createGift(CreateGiftDTO gift) throws Exception;

    public void updateGift(UpdateGiftDTO gift) throws Exception;

    public void deleteGift(DeleteGiftDTO ids) throws Exception;

    public void deleteAllByAccount(UUID accountId) throws Exception;

    public Gift getGiftById(UUID giftId) throws Exception;

    public List<Gift> getAllGifts(UUID accountId);

    public Page<Gift> getAllGifts(UUID accountId, Pageable page);

    public Page<Gift> getByTitleOrBoutght(SearcherByTitleDTO searcher, UUID accountId, Pageable page);

    public Page<Gift> getByCategoriesOrBought(SearcherByCategoriesDTO searcher, UUID accountId, Pageable page);

    public Page<Gift> getByPriceOrBought(SearcherByPriceDTO searcher, UUID accountId, Pageable page);

    public Page<Gift> getByTitleAndCategoriesOrBought(SearcherByTitleAndCategoriesDTO searcher, UUID accountId, Pageable page);

    public Page<Gift> getByTitleAndPriceOrBought(SearcherByTitleAndPriceDTO searcher, UUID accountId, Pageable page);

    public Page<Gift> getByCategoriesAndPriceOrBought(SearcherByCategoriesAndPriceDTO searcher, UUID accountId, Pageable page);

    public Page<Gift> getAllFilters(SearcherDTO searcher, UUID accountId, Pageable page);

}
