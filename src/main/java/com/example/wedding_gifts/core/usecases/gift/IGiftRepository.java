package com.example.wedding_gifts.core.usecases.gift;

import java.util.List;
import java.util.UUID;

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

    public List<Gift> getByTitleOrBoutght(SearcherByTitleDTO searcher, UUID accountId);

    public List<Gift> getByCategoriesOrBought(SearcherByCategoriesDTO searcher, UUID accountId);

    public List<Gift> getByPriceOrBought(SearcherByPriceDTO searcher, UUID accountId);

    public List<Gift> getByTitleAndCategoriesOrBought(SearcherByTitleAndCategoriesDTO searcher, UUID accountId);

    public List<Gift> getByTitleAndPriceOrBought(SearcherByTitleAndPriceDTO searcher, UUID accountId);

    public List<Gift> getByCategoriesAndPriceOrBought(SearcherByCategoriesAndPriceDTO searcher, UUID accountId);

    public List<Gift> getAllFilters(SearcherDTO searcher, UUID accountId);

}
