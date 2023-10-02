package com.example.wedding_gifts.application.services;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
import com.example.wedding_gifts.core.usecases.gift.GiftRepository;
import com.example.wedding_gifts.core.usecases.gift.GiftUseCase;

@Service
public class GiftServicesImpl implements GiftUseCase {

    @Autowired
    GiftRepository repository;

    @Override
    public Gift createGift(CreateGiftDTO gift) throws Exception {
        return repository.createGift(gift);
    }

    @Override
    public void updateGift(UpdateGiftDTO gift) throws Exception {
        repository.updateGift(gift);
    }

    @Override
    public void deleteGift(DeleteGiftDTO ids) throws Exception {
        repository.deleteGift(ids);
    }

    @Override
    public List<Gift> getAllGifts(UUID accountId) throws Exception {
        return repository.getAllGifts(accountId);
    }

    @Override
    public List<Gift> getWithFilter(SearcherDTO searcher, UUID accountId) throws Exception {
        if(searcher.title() != null && searcher.categories() != null && (searcher.startPrice() != null || searcher.endPrice() != null)){ 
            return repository.getAllFilters(searcherDTO(searcher), accountId);
        }

        if(searcher.title() != null){
            if(searcher.categories() != null) {
                return repository.getByTitleAndCategoriesOrBought(searcherByTitleAndCategoriesDTO(searcher), accountId);
            }

            if(searcher.startPrice() != null || searcher.endPrice() != null) {
                return repository.getByTitleAndPriceOrBought(searcherByTitleAndPriceDTO(searcher), accountId);
            }

            return repository.getByTitleOrBoutght(searcherByTitleDTO(searcher), accountId);
        }

        if(searcher.categories() != null) {
            if(searcher.startPrice() != null || searcher.endPrice() != null) {
                return repository.getByCategoriesAndPriceOrBought(searcherByCategoriesAndPriceDTO(searcher), accountId);
            }

            return repository.getByCategoriesOrBought(searcherByCategoriesDTO(searcher), accountId);
        }

        if(searcher.startPrice() != null || searcher.endPrice() != null) {
            return repository.getByPriceOrBought(searcherByPriceDTO(searcher), accountId);
        }

        throw new Exception("Filters are null");
    }

    private SearcherDTO searcherDTO(SearcherDTO searcher) {
        return new SearcherDTO(
            searcher.isBought() != null ? searcher.isBought() : null, 
            searcher.title(), 
            searcher.startPrice() != null ? searcher.startPrice() : BigDecimal.ZERO, 
            searcher.endPrice() != null ? searcher.endPrice() : BigDecimal.valueOf(Double.MAX_VALUE),
            searcher.categories() 
        );
    }

    private SearcherByTitleAndCategoriesDTO searcherByTitleAndCategoriesDTO(SearcherDTO searcher) {
        return new SearcherByTitleAndCategoriesDTO(
            searcher.title(), 
            searcher.categories(), 
            searcher.isBought() != null ? searcher.isBought() : null
        );
    }

    private SearcherByTitleAndPriceDTO searcherByTitleAndPriceDTO(SearcherDTO searcher) {
        return new SearcherByTitleAndPriceDTO(
            searcher.title(), 
            searcher.startPrice() != null ? searcher.startPrice() : BigDecimal.ZERO, 
            searcher.endPrice() != null ? searcher.endPrice() : BigDecimal.valueOf(Double.MAX_VALUE),
            searcher.isBought() != null ? searcher.isBought() : null 
        );
    }

    private SearcherByTitleDTO searcherByTitleDTO(SearcherDTO searcher) {
        return new SearcherByTitleDTO(
            searcher.title(), 
            searcher.isBought() != null ? searcher.isBought() : null
        );
    }

    private SearcherByCategoriesAndPriceDTO searcherByCategoriesAndPriceDTO(SearcherDTO searcher) {
        return new SearcherByCategoriesAndPriceDTO(
            searcher.categories(), 
            searcher.startPrice() != null ? searcher.startPrice() : BigDecimal.ZERO, 
            searcher.endPrice() != null ? searcher.endPrice() : BigDecimal.valueOf(Double.MAX_VALUE),
            searcher.isBought() != null ? searcher.isBought() : null 
        );
    }

    private SearcherByCategoriesDTO searcherByCategoriesDTO(SearcherDTO searcher) {
        return new SearcherByCategoriesDTO(
            searcher.categories(), 
            searcher.isBought() != null ? searcher.isBought() : null
        );
    }

    private SearcherByPriceDTO searcherByPriceDTO(SearcherDTO searcher) {
        return new SearcherByPriceDTO(
            searcher.startPrice() != null ? searcher.startPrice() : BigDecimal.ZERO, 
            searcher.endPrice() != null ? searcher.endPrice() : BigDecimal.valueOf(Double.MAX_VALUE),
            searcher.isBought() != null ? searcher.isBought() : null 
        );
    }
    
}
