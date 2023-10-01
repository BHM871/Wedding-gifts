package com.example.wedding_gifts.application.repositories;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.example.wedding_gifts.core.domain.dtos.gift.CreateGiftDTO;
import com.example.wedding_gifts.core.domain.dtos.gift.UpdateGiftDTO;
import com.example.wedding_gifts.core.domain.dtos.gift.searchers.SearcherByCategoriesAndPriceDTO;
import com.example.wedding_gifts.core.domain.dtos.gift.searchers.SearcherByCategoriesDTO;
import com.example.wedding_gifts.core.domain.dtos.gift.searchers.SearcherByPriceDTO;
import com.example.wedding_gifts.core.domain.dtos.gift.searchers.SearcherByTitleAndCategoriesDTO;
import com.example.wedding_gifts.core.domain.dtos.gift.searchers.SearcherByTitleAndPriceDTO;
import com.example.wedding_gifts.core.domain.dtos.gift.searchers.SearcherByTitleDTO;
import com.example.wedding_gifts.core.domain.dtos.gift.searchers.SearcherDTO;
import com.example.wedding_gifts.core.domain.model.Account;
import com.example.wedding_gifts.core.domain.model.CategoriesEnum;
import com.example.wedding_gifts.core.domain.model.Gift;
import com.example.wedding_gifts.core.usecases.account.AccountRepository;
import com.example.wedding_gifts.core.usecases.gift.GiftRepository;
import com.example.wedding_gifts.infra.jpa.JpaGiftRepository;

@Repository
public class GiftRepositoryImpl implements GiftRepository {

    @Autowired
    JpaGiftRepository thisJpaRepository;
    @Autowired
    AccountRepository accountRepository;

    @Override
    public Gift save(Gift gift) throws Exception {
        return thisJpaRepository.save(gift);
    }

    @Override
    public Gift createGift(CreateGiftDTO gift) throws Exception {
        Gift newGift = new Gift(gift);
        Account account  = accountRepository.getAccountById(gift.accountId());

        newGift.setAccount(account);

        return save(newGift);
    }

    @Override
    public void updateGift(UpdateGiftDTO gift, UUID id) throws Exception {
        Gift updateGift = getById(id);

        updateGift.update(gift);

        save(updateGift);
    }

    @Override
    public void deleteGift(UUID id) throws Exception {
        Gift gift = getById(id);
        thisJpaRepository.delete(gift);
    }

    @Override
    public Gift getById(UUID id) throws Exception {
        return thisJpaRepository.findById(id).orElseThrow(() -> new Exception("Gift not found"));
    }

    @Override
    public List<Gift> getAllGifts() {
        return thisJpaRepository.findAll();
    }

    @Override
    public List<Gift> getByTitleOrBoutght(SearcherByTitleDTO searcher) {
        if(searcher.isBought() == null) {
            return thisJpaRepository.findByTitle(searcher.title());
        } else {
            return thisJpaRepository.findByTitleAndIsBought(searcher.title(), searcher.isBought());
        }
    }

    @Override
    public List<Gift> getByCategoriesOrBought(SearcherByCategoriesDTO searcher) {
        Set<Gift> out = new HashSet<Gift>();
        
        if(searcher.isBought() == null) {
            for(CategoriesEnum cat : searcher.categories()) out.addAll(thisJpaRepository.findByCategories(cat));
        } else {
            for(CategoriesEnum cat : searcher.categories()) out.addAll(thisJpaRepository.findByCategoriesAndIsBought(cat, searcher.isBought()));
        }

        return List.copyOf(out);
    }

    @Override
    public List<Gift> getByPriceOrBought(SearcherByPriceDTO searcher) {
        if(searcher.isBought() == null) {
            return thisJpaRepository.findByPriceBetween(searcher.startPrice(), searcher.endPrice());
        } else {
            return thisJpaRepository.findByPriceBetweenAndIsBought(searcher.startPrice(), searcher.endPrice(), searcher.isBought());
        }
    }

    @Override
    public List<Gift> getByCategoriesAndPriceOrBought(SearcherByCategoriesAndPriceDTO searcher) {
        Set<Gift> out = new HashSet<Gift>();
        
        if(searcher.isBought() == null) {
            for(CategoriesEnum cat : searcher.categories()) out.addAll(thisJpaRepository.findByCategoriesAndPriceBetween(cat, searcher.startPrice(), searcher.endPrice()));
        } else {
            for(CategoriesEnum cat : searcher.categories()) out.addAll(thisJpaRepository.findByCategoriesAndPriceBetweenAndIsBought(cat, searcher.startPrice(), searcher.endPrice(), searcher.isBought()));
        }

        return List.copyOf(out);
    }

    @Override
    public List<Gift> getByTitleAndCategoriesOrBought(SearcherByTitleAndCategoriesDTO searcher) {
        Set<Gift> out = new HashSet<Gift>();
        
        if(searcher.isBought() == null) {
            for(CategoriesEnum cat : searcher.categories()) out.addAll(thisJpaRepository.findByTitleAndCategories(searcher.title(), cat));
        } else {
            for(CategoriesEnum cat : searcher.categories()) out.addAll(thisJpaRepository.findByTitleAndCategoriesAndIsBought(searcher.title(), cat, searcher.isBought()));
        }

        return List.copyOf(out);
    }

    @Override
    public List<Gift> getByTitleAndPriceOrBought(SearcherByTitleAndPriceDTO searcher) {
        if(searcher.isBought() == null) {
            return thisJpaRepository.findByTitleAndPriceBetween(searcher.title(), searcher.startPrice(), searcher.endPrice());
        } else {
            return thisJpaRepository.findByTitleAndPriceBetweenAndIsBought(searcher.title(), searcher.startPrice(), searcher.endPrice(), searcher.isBought());
        }
    }

    @Override
    public List<Gift> getAllFilters(SearcherDTO searcher) {
        Set<Gift> out = new HashSet<Gift>();
        
        if(searcher.isBought() == null) {
            for(CategoriesEnum cat : searcher.categories()) out.addAll(thisJpaRepository.findByTitleAndCategories(searcher.title(), cat));
        } else {
            for(CategoriesEnum cat : searcher.categories()) out.addAll(thisJpaRepository.findByTitleAndCategoriesAndIsBought(searcher.title(), cat, searcher.isBought()));
        }

        return List.copyOf(out);
    }    
    
}
