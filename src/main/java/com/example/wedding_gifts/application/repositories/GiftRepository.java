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
import com.example.wedding_gifts.core.domain.exceptions.account.AccountNotFoundException;
import com.example.wedding_gifts.core.domain.exceptions.gift.GiftExecutionException;
import com.example.wedding_gifts.core.domain.exceptions.gift.GiftNotFoundException;
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
        try {
            return thisJpaRepository.save(gift);
        } catch (Exception e){
            throw new GiftExecutionException("Gift can't be saved", e);
        }
    }

    @Override
    public Gift createGift(CreateGiftDTO gift) throws Exception {
        try{
            Gift newGift = new Gift(gift);
            Account account  = accountRepository.getAccountById(gift.accountId());

            newGift.setAccount(account);

            return save(newGift);
        } catch (AccountNotFoundException e){
            throw e;
        } catch (GiftExecutionException e) {
            throw e;
        } catch (Exception e) {
            throw new GiftExecutionException("Gift can't be created", e);
        }
    }

    @Override
    public void updateGift(UpdateGiftDTO upGift) throws Exception {
        try{
            Gift updateGift = getGiftById(upGift.giftId());

            int compared = updateGift.getAccount().getId()
                                .compareTo(upGift.accountId());
            if(compared != 0) throw new Exception("This gift is not your");

            updateGift.update(upGift);

            save(updateGift);
        } catch(GiftNotFoundException e){
            throw e;
        } catch (GiftExecutionException e){
            throw e;
        } catch (Exception e) {
            throw new GiftExecutionException("Gift can't be updated", e);
        }
    }

    @Override
    public void deleteGift(DeleteGiftDTO ids) throws Exception {
        try{
            Gift gift = getGiftById(ids.giftId());
            
            int compared = gift.getAccount().getId()
                            .compareTo(ids.accountId());
            if(compared != 0) throw new Exception("This gift is not your");

            thisJpaRepository.delete(gift);
        } catch(GiftNotFoundException e){
            throw e;
        } catch (Exception e){
            throw new GiftExecutionException("Gift can't be deleted", e);
        }
    }

    @Override
    public void deleteAllByAccount(UUID accountId) throws Exception { 
        try{       
            accountRepository.getAccountById(accountId);
            List<Gift> gifts = getAllGifts(accountId);

            if(gifts != null && !gifts.isEmpty()) thisJpaRepository.deleteAll(gifts);
        } catch (AccountNotFoundException e){
            throw e;
        } catch (Exception e){
            throw new GiftExecutionException("Some Gift can't be deleted, but some was deleted", e);
        }
    }

    @Override
    public Gift getGiftById(UUID id) throws Exception {
        return thisJpaRepository.findById(id).orElseThrow(() -> new GiftNotFoundException("Gift not found"));
    }

    @Override
    public List<Gift> getAllGifts(UUID accountId) {
        return thisJpaRepository.findAllByAccount(accountId);
    }

    @Override
    public List<Gift> getByTitleOrBoutght(SearcherByTitleDTO searcher, UUID accountId) {

        if(searcher.isBought() == null) {
            return thisJpaRepository.findByTitleAndAccount(searcher.title(), accountId);
        } else {
            return thisJpaRepository.findByTitleAndIsBoughtAndAccount(searcher.title(), searcher.isBought(), accountId);
        }

    }

    @Override
    public List<Gift> getByCategoriesOrBought(SearcherByCategoriesDTO searcher, UUID accountId) {
        Set<Gift> out = new HashSet<Gift>();
        
        if(searcher.isBought() == null) {
            for(CategoriesEnum cat : searcher.categories()) out.addAll(thisJpaRepository.findByCategoriesAndAccount(cat.toString(), accountId));
        } else {
            for(CategoriesEnum cat : searcher.categories()) out.addAll(thisJpaRepository.findByCategoriesAndIsBoughtAndAccount(cat.toString(), searcher.isBought(), accountId));
        }

        return List.copyOf(out);
    }

    @Override
    public List<Gift> getByPriceOrBought(SearcherByPriceDTO searcher, UUID accountId) {

        if(searcher.isBought() == null) {
            return thisJpaRepository.findByPriceBetweenAndAccount(searcher.startPrice(), searcher.endPrice(), accountId);
        } else {
            return thisJpaRepository.findByPriceBetweenAndIsBoughtAndAccount(searcher.startPrice(), searcher.endPrice(), searcher.isBought(), accountId);
        }

    }

    @Override
    public List<Gift> getByCategoriesAndPriceOrBought(SearcherByCategoriesAndPriceDTO searcher, UUID accountId) {
        Set<Gift> out = new HashSet<Gift>();
        
        if(searcher.isBought() == null) {
            for(CategoriesEnum cat : searcher.categories()) out.addAll(thisJpaRepository.findByCategoriesAndPriceBetweenAndAccount(cat.toString(), searcher.startPrice(), searcher.endPrice(), accountId));
        } else {
            for(CategoriesEnum cat : searcher.categories()) out.addAll(thisJpaRepository.findByCategoriesAndPriceBetweenAndIsBoughtAndAccount(cat.toString(), searcher.startPrice(), searcher.endPrice(), searcher.isBought(), accountId));
        }

        return List.copyOf(out);
    }

    @Override
    public List<Gift> getByTitleAndCategoriesOrBought(SearcherByTitleAndCategoriesDTO searcher, UUID accountId) {
        Set<Gift> out = new HashSet<Gift>();
        
        if(searcher.isBought() == null) {
            for(CategoriesEnum cat : searcher.categories()) out.addAll(thisJpaRepository.findByTitleAndCategoriesAndAccount(searcher.title(), cat.toString(), accountId));
        } else {
            for(CategoriesEnum cat : searcher.categories()) out.addAll(thisJpaRepository.findByTitleAndCategoriesAndIsBoughtAndAccount(searcher.title(), cat.toString(), searcher.isBought(), accountId));
        }

        return List.copyOf(out);
    }

    @Override
    public List<Gift> getByTitleAndPriceOrBought(SearcherByTitleAndPriceDTO searcher, UUID accountId) {

        if(searcher.isBought() == null) {
            return thisJpaRepository.findByTitleAndPriceBetweenAndAccount(searcher.title(), searcher.startPrice(), searcher.endPrice(), accountId);
        } else {
            return thisJpaRepository.findByTitleAndPriceBetweenAndIsBoughtAndAccount(searcher.title(), searcher.startPrice(), searcher.endPrice(), searcher.isBought(), accountId);
        }

    }

    @Override
    public List<Gift> getAllFilters(SearcherDTO searcher, UUID accountId) {
        Set<Gift> out = new HashSet<Gift>();
        
        if(searcher.isBought() == null) {
            for(CategoriesEnum cat : searcher.categories()) out.addAll(thisJpaRepository.findByTitleAndCategoriesAndPriceBetweenAndAccount(searcher.title(), cat.toString(), searcher.startPrice(), searcher.endPrice(), accountId));
        } else {
            for(CategoriesEnum cat : searcher.categories()) out.addAll(thisJpaRepository.findByTitleAndCategoriesAndPriceBetweenAndIsBoughtAndAccount(searcher.title(), cat.toString(), searcher.startPrice(), searcher.endPrice(), searcher.isBought(), accountId));
        }

        return List.copyOf(out);
    }    
    
}
