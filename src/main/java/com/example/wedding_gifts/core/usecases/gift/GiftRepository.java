package com.example.wedding_gifts.core.usecases.gift;

import java.util.List;

import com.example.wedding_gifts.core.domain.dtos.gift.CreateGiftDTO;
import com.example.wedding_gifts.core.domain.dtos.gift.SearcherByCategoriesDTO;
import com.example.wedding_gifts.core.domain.dtos.gift.SearcherByPriceDTO;
import com.example.wedding_gifts.core.domain.dtos.gift.SearcherByTitleDTO;
import com.example.wedding_gifts.core.domain.dtos.gift.UpdateGiftDTO;
import com.example.wedding_gifts.core.domain.model.Gift;

public interface GiftRepository {
    
    public Gift save(Gift gift) throws Exception;

    public Gift createGift(CreateGiftDTO gift) throws Exception;

    public void updateGift(UpdateGiftDTO gift) throws Exception;

    public List<Gift> getAllGifts();

    public List<Gift> getByTitleOrBoutght(SearcherByTitleDTO searcher);

    public List<Gift> getByCategoriesOrBought(SearcherByCategoriesDTO searcher);

    public List<Gift> getByPreiceOrBought(SearcherByPriceDTO searcher);

}
