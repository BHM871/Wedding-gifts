package com.example.wedding_gifts.application.repositories;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

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
import com.example.wedding_gifts.core.domain.model.Account;
import com.example.wedding_gifts.core.domain.model.CategoriesEnum;
import com.example.wedding_gifts.core.domain.model.Gift;
import com.example.wedding_gifts.core.usecases.account.IAccountRepository;
import com.example.wedding_gifts.core.usecases.gift.IGiftRepository;
import com.example.wedding_gifts.infra.jpa.JpaGiftRepository;

@Repository
public class GiftRepository implements IGiftRepository {

    @Autowired
    private JpaGiftRepository thisJpaRepository;
    @Autowired
    private IAccountRepository accountRepository;

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
    public void updateGift(UpdateGiftDTO upGift) throws Exception {
        Gift updateGift = getGiftById(upGift.giftId());

        int compared = updateGift.getAccount().getId()
                            .compareTo(upGift.accountId());
        if(compared != 0) throw new Exception("This gift is not your");

        updateGift.update(upGift);

        save(updateGift);
    }

    @Override
    public void deleteGift(DeleteGiftDTO ids) throws Exception {
        Gift gift = getGiftById(ids.giftId());
        
        int compared = gift.getAccount().getId()
                        .compareTo(ids.accountId());
        if(compared != 0) throw new Exception("This gift is not your");

        thisJpaRepository.delete(gift);
    }

    @Override
    public void deleteAllByAccount(UUID accountId) throws Exception {        
        accountRepository.getAccountById(accountId);
        List<Gift> gifts = getAllGifts(accountId);

        if(gifts != null && !gifts.isEmpty()) thisJpaRepository.deleteAll(gifts);
    }

    @Override
    public Gift getGiftById(UUID id) throws Exception {
        return thisJpaRepository.findById(id).orElseThrow(() -> new Exception("Gift not found"));
    }

    @Override
    public List<Gift> getAllGifts(UUID accountId) throws Exception {
        return thisJpaRepository.findAllByAccount(accountId);
    }

    @Override
    public List<Gift> getByTitleOrBoutght(SearcherByTitleDTO searcher, UUID accountId) throws Exception {

        if(searcher.isBought() == null) {
            return thisJpaRepository.findByTitleAndAccount(searcher.title(), accountId);
        } else {
            return thisJpaRepository.findByTitleAndIsBoughtAndAccount(searcher.title(), searcher.isBought(), accountId);
        }
    }

    @Override
    public List<Gift> getByCategoriesOrBought(SearcherByCategoriesDTO searcher, UUID accountId) throws Exception {
        Set<Gift> out = new HashSet<Gift>();
        
        if(searcher.isBought() == null) {
            for(CategoriesEnum cat : searcher.categories()) out.addAll(thisJpaRepository.findByCategoriesAndAccount(cat.toString(), accountId));
        } else {
            for(CategoriesEnum cat : searcher.categories()) out.addAll(thisJpaRepository.findByCategoriesAndIsBoughtAndAccount(cat.toString(), searcher.isBought(), accountId));
        }

        return List.copyOf(out);
    }

    @Override
    public List<Gift> getByPriceOrBought(SearcherByPriceDTO searcher, UUID accountId) throws Exception {

        if(searcher.isBought() == null) {
            return thisJpaRepository.findByPriceBetweenAndAccount(searcher.startPrice(), searcher.endPrice(), accountId);
        } else {
            return thisJpaRepository.findByPriceBetweenAndIsBoughtAndAccount(searcher.startPrice(), searcher.endPrice(), searcher.isBought(), accountId);
        }
    }

    @Override
    public List<Gift> getByCategoriesAndPriceOrBought(SearcherByCategoriesAndPriceDTO searcher, UUID accountId) throws Exception {
        Set<Gift> out = new HashSet<Gift>();
        
        if(searcher.isBought() == null) {
            for(CategoriesEnum cat : searcher.categories()) out.addAll(thisJpaRepository.findByCategoriesAndPriceBetweenAndAccount(cat.toString(), searcher.startPrice(), searcher.endPrice(), accountId));
        } else {
            for(CategoriesEnum cat : searcher.categories()) out.addAll(thisJpaRepository.findByCategoriesAndPriceBetweenAndIsBoughtAndAccount(cat.toString(), searcher.startPrice(), searcher.endPrice(), searcher.isBought(), accountId));
        }

        return List.copyOf(out);
    }

    @Override
    public List<Gift> getByTitleAndCategoriesOrBought(SearcherByTitleAndCategoriesDTO searcher, UUID accountId) throws Exception {
        Set<Gift> out = new HashSet<Gift>();
        
        if(searcher.isBought() == null) {
            for(CategoriesEnum cat : searcher.categories()) out.addAll(thisJpaRepository.findByTitleAndCategoriesAndAccount(searcher.title(), cat.toString(), accountId));
        } else {
            for(CategoriesEnum cat : searcher.categories()) out.addAll(thisJpaRepository.findByTitleAndCategoriesAndIsBoughtAndAccount(searcher.title(), cat.toString(), searcher.isBought(), accountId));
        }

        return List.copyOf(out);
    }

    @Override
    public List<Gift> getByTitleAndPriceOrBought(SearcherByTitleAndPriceDTO searcher, UUID accountId) throws Exception {

        if(searcher.isBought() == null) {
            return thisJpaRepository.findByTitleAndPriceBetweenAndAccount(searcher.title(), searcher.startPrice(), searcher.endPrice(), accountId);
        } else {
            return thisJpaRepository.findByTitleAndPriceBetweenAndIsBoughtAndAccount(searcher.title(), searcher.startPrice(), searcher.endPrice(), searcher.isBought(), accountId);
        }
    }

    @Override
    public List<Gift> getAllFilters(SearcherDTO searcher, UUID accountId) throws Exception {
        Set<Gift> out = new HashSet<Gift>();
        
        if(searcher.isBought() == null) {
            for(CategoriesEnum cat : searcher.categories()) out.addAll(thisJpaRepository.findByTitleAndCategoriesAndPriceBetweenAndAccount(searcher.title(), cat.toString(), searcher.startPrice(), searcher.endPrice(), accountId));
        } else {
            for(CategoriesEnum cat : searcher.categories()) out.addAll(thisJpaRepository.findByTitleAndCategoriesAndPriceBetweenAndIsBoughtAndAccount(searcher.title(), cat.toString(), searcher.startPrice(), searcher.endPrice(), searcher.isBought(), accountId));
        }

        return List.copyOf(out);
    }    
    
}
