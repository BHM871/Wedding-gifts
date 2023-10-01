package com.example.wedding_gifts.application.services;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.wedding_gifts.core.domain.dtos.gift.CreateGiftDTO;
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
    public void updateGift(UpdateGiftDTO gift, UUID id) throws Exception {
        repository.updateGift(gift, id);
    }

    @Override
    public void deleteGift(UUID id) throws Exception {
        repository.deleteGift(id);
    }

    @Override
    public List<Gift> getAllGifts() {
        return repository.getAllGifts();
    }

    @Override
    public List<Gift> getWithFilter(SearcherDTO searcher) throws Exception {
        if(searcher.title() != null && searcher.categories() != null && (searcher.startPrice() != null || searcher.endPrice() != null)){
            SearcherDTO newSearcher = searcherDTO(searcher);
            return repository.getAllFilters(newSearcher);
        }

        if(searcher.title() != null){
            if(searcher.categories() != null) {
                return repository.getByTitleAndCategoriesOrBought(
                    new SearcherByTitleAndCategoriesDTO(searcher.title(), searcher.categories(), searcher.isBought() != null ? searcher.isBought() : null)
                );
            }

            if(searcher.startPrice() != null || searcher.endPrice() != null) {
                SearcherByTitleAndPriceDTO newSearcher = searcherByTitleAndPriceDTO(searcher);
                return repository.getByTitleAndPriceOrBought(newSearcher);
            }

            return repository.getByTitleOrBoutght(
                new SearcherByTitleDTO(searcher.title(), searcher.isBought() != null ? searcher.isBought() : null)
            );
        }

        if(searcher.categories() != null) {
            if(searcher.startPrice() != null || searcher.endPrice() != null) {
                SearcherByCategoriesAndPriceDTO newSearcher = searcherByCategoriesAndPriceDTO(searcher);
                return repository.getByCategoriesAndPriceOrBought(newSearcher);
            }

            return repository.getByCategoriesOrBought(
                new SearcherByCategoriesDTO(searcher.categories(), searcher.isBought() != null ? searcher.isBought() : null)
            );
        }

        if(searcher.startPrice() != null || searcher.endPrice() != null) {
            SearcherByPriceDTO newSearcher = searcherByPriceDTO(searcher);
            return repository.getByPriceOrBought(newSearcher);
        }

        throw new Exception("Filters are null");
    }

    private SearcherDTO searcherDTO(SearcherDTO searcher){
        SearcherDTO newSearcher;

        if(searcher.startPrice() == null) {
            newSearcher = new SearcherDTO(
                searcher.isBought() != null ? searcher.isBought() : null, 
                searcher.title(), 
                BigDecimal.ZERO, 
                searcher.endPrice(),
                searcher.categories() 
            );
        } else if(searcher.endPrice() == null) {
            newSearcher = new SearcherDTO(
                searcher.isBought() != null ? searcher.isBought() : null, 
                searcher.title(), 
                searcher.startPrice(), 
                BigDecimal.valueOf(Double.MAX_VALUE),
                searcher.categories() 
            );
        } else {
            newSearcher = searcher;
        }

        return newSearcher;
    }

    private SearcherByTitleAndPriceDTO searcherByTitleAndPriceDTO(SearcherDTO searcher) {
        SearcherByTitleAndPriceDTO newSearcher;

        if(searcher.startPrice() == null) {
            newSearcher = new SearcherByTitleAndPriceDTO( 
                searcher.title(),
                BigDecimal.ZERO, 
                searcher.endPrice(),
                searcher.isBought() != null ? searcher.isBought() : null
            );
        } else if(searcher.endPrice() == null) {
            newSearcher = new SearcherByTitleAndPriceDTO(
                searcher.title(), 
                searcher.startPrice(), 
                BigDecimal.valueOf(Double.MAX_VALUE),
                searcher.isBought() != null ? searcher.isBought() : null 
            );
        } else {
            newSearcher = new SearcherByTitleAndPriceDTO(
                searcher.title(), 
                searcher.startPrice(), 
                searcher.endPrice(), 
                searcher.isBought() != null ? searcher.isBought() : null);
        }

        return newSearcher;
    }

    private SearcherByCategoriesAndPriceDTO searcherByCategoriesAndPriceDTO(SearcherDTO searcher) {
        SearcherByCategoriesAndPriceDTO newSearcher;

        if(searcher.startPrice() == null) {
            newSearcher = new SearcherByCategoriesAndPriceDTO( 
                searcher.categories(),
                BigDecimal.ZERO, 
                searcher.endPrice(),
                searcher.isBought() != null ? searcher.isBought() : null
            );
        } else if(searcher.endPrice() == null) {
            newSearcher = new SearcherByCategoriesAndPriceDTO(
                searcher.categories(), 
                searcher.startPrice(), 
                BigDecimal.valueOf(Double.MAX_VALUE),
                searcher.isBought() != null ? searcher.isBought() : null 
            );
        } else {
            newSearcher = new SearcherByCategoriesAndPriceDTO(
                searcher.categories(), 
                searcher.startPrice(), 
                searcher.endPrice(), 
                searcher.isBought() != null ? searcher.isBought() : null);
        }

        return newSearcher;
    }

    private SearcherByPriceDTO searcherByPriceDTO(SearcherDTO searcher) {
        SearcherByPriceDTO newSearcher;

        if(searcher.startPrice() == null) {
            newSearcher = new SearcherByPriceDTO(
                BigDecimal.ZERO, 
                searcher.endPrice(),
                searcher.isBought() != null ? searcher.isBought() : null
            );
        } else if(searcher.endPrice() == null) {
            newSearcher = new SearcherByPriceDTO(
                searcher.startPrice(), 
                BigDecimal.valueOf(Double.MAX_VALUE),
                searcher.isBought() != null ? searcher.isBought() : null 
            );
        } else {
            newSearcher = new SearcherByPriceDTO(
                searcher.startPrice(), 
                searcher.endPrice(),
                searcher.isBought() != null ? searcher.isBought() : null 
            );
        }

        return newSearcher;
    }
    
}
