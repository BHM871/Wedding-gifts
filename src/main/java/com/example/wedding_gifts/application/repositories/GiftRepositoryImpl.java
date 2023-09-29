package com.example.wedding_gifts.application.repositories;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.wedding_gifts.core.domain.dtos.gift.CreateGiftDTO;
import com.example.wedding_gifts.core.domain.dtos.gift.SearcherByCategoriesDTO;
import com.example.wedding_gifts.core.domain.dtos.gift.SearcherByPriceDTO;
import com.example.wedding_gifts.core.domain.dtos.gift.SearcherByTitleDTO;
import com.example.wedding_gifts.core.domain.dtos.gift.UpdateGiftDTO;
import com.example.wedding_gifts.core.domain.model.Account;
import com.example.wedding_gifts.core.domain.model.Gift;
import com.example.wedding_gifts.core.usecases.account.AccountRepository;
import com.example.wedding_gifts.core.usecases.gift.GiftRepository;
import com.example.wedding_gifts.infra.jpa.JpaGiftRepository;

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
    public void delete(UUID id) throws Exception {
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
            return thisJpaRepository.findByTitleAndBought(searcher.title(), searcher.isBought());
        }
    }

    @Override
    public List<Gift> getByCategoriesOrBought(SearcherByCategoriesDTO searcher) {
        if(searcher.isBought() == null) {
            return thisJpaRepository.findByCategories(searcher.categories());
        } else {
            return thisJpaRepository.findByCategoriesAndBought(searcher.categories(), searcher.isBought());
        }
    }

    @Override
    public List<Gift> getByPriceOrBought(SearcherByPriceDTO searcher) {
        if(searcher.isBought() == null) {
            return thisJpaRepository.findByPriceBetween(searcher.startPrice(),searcher.endPrice());
        } else {
            return thisJpaRepository.findByPriceBetweenAndBought(searcher.startPrice(), searcher.endPrice(), searcher.isBought());
        }
    }
    
}
