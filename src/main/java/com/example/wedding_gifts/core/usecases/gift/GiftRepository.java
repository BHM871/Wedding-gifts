package com.example.wedding_gifts.core.usecases.gift;

import java.util.List;
import java.util.UUID;

import com.example.wedding_gifts.core.domain.dtos.gift.CreateGiftDTO;
import com.example.wedding_gifts.core.domain.dtos.gift.UpdateGiftDTO;
import com.example.wedding_gifts.core.domain.dtos.gift.searchers.SearcherByCategoriesAndPriceDTO;
import com.example.wedding_gifts.core.domain.dtos.gift.searchers.SearcherByCategoriesDTO;
import com.example.wedding_gifts.core.domain.dtos.gift.searchers.SearcherByPriceDTO;
import com.example.wedding_gifts.core.domain.dtos.gift.searchers.SearcherByTitleAndCategoriesDTO;
import com.example.wedding_gifts.core.domain.dtos.gift.searchers.SearcherByTitleAndPriceDTO;
import com.example.wedding_gifts.core.domain.dtos.gift.searchers.SearcherByTitleDTO;
import com.example.wedding_gifts.core.domain.model.Gift;

public interface GiftRepository {
    
    public Gift save(Gift gift) throws Exception;

    public Gift createGift(CreateGiftDTO gift) throws Exception;

    public void updateGift(UpdateGiftDTO gift, UUID id) throws Exception;

    public void deleteGift(UUID id) throws Exception;

    public Gift getById(UUID id) throws Exception;

    public List<Gift> getAllGifts();

    public List<Gift> getByTitleOrBoutght(SearcherByTitleDTO searcher);

    public List<Gift> getByCategoriesOrBought(SearcherByCategoriesDTO searcher);

    public List<Gift> getByPriceOrBought(SearcherByPriceDTO searcher);

    public List<Gift> getByTitleAndCategoriesOrBought(SearcherByTitleAndCategoriesDTO searcher);

    public List<Gift> getByTitleAndPriceOrBought(SearcherByTitleAndPriceDTO searcher);

    public List<Gift> getByCategoriesAndPriceOrBought(SearcherByCategoriesAndPriceDTO searcher);

}
