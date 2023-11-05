package com.example.wedding_gifts.application.gift;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
import com.example.wedding_gifts.core.domain.exceptions.common.MyException;
import com.example.wedding_gifts.core.domain.exceptions.gift.GiftExecutionException;
import com.example.wedding_gifts.core.domain.exceptions.gift.GiftNotFoundException;
import com.example.wedding_gifts.core.domain.exceptions.gift.GiftNotYourException;
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
            Account account = accountRepository.getAccountById(gift.accountId());

            newGift.setAccount(account);

            return save(newGift);
        } catch (MyException e){
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
            if(compared != 0) throw new GiftNotYourException("This gift is not your");

            updateGift.update(upGift);

            save(updateGift);
        } catch(MyException e){
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
            if(compared != 0) throw new GiftNotYourException("This gift is not your");

            thisJpaRepository.delete(gift);
        } catch(MyException e){
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
        } catch (MyException e){
            throw e;
        } catch (Exception e){
            throw new GiftExecutionException("Some Gift can't be deleted, but some was deleted", e);
        }
    }

    @Override
    public Gift getGiftById(UUID id) throws Exception {
        return thisJpaRepository.findById(id).orElseThrow(() -> new GiftNotFoundException("Gift not exists"));
    }

    @Override
    public List<Gift> getAllGifts(UUID accountId) {
        return thisJpaRepository.findAllByAccount(accountId);
    }

    @Override
    public Page<Gift> getAllGifts(UUID accountId, Pageable paging) {
        return thisJpaRepository.findAllByAccount(accountId, paging);
    }

    @Override
    public Page<Gift> getByTitleOrBoutght(SearcherByTitleDTO searcher, UUID accountId, Pageable paging) {
        if(searcher.isBought() == null) {
            return thisJpaRepository.findByTitleAndAccount(searcher.title(), accountId, paging);
        } else {
            return thisJpaRepository.findByTitleAndIsBoughtAndAccount(searcher.title(), searcher.isBought(), accountId, paging);
        }

    }

    @Override
    public Page<Gift> getByCategoriesOrBought(SearcherByCategoriesDTO searcher, UUID accountId, Pageable paging) {
        
        if(searcher.isBought() == null) {
            return thisJpaRepository.findByCategoriesAndAccount(generatedArray(searcher.categories()), accountId, paging);
        } else {
            return thisJpaRepository.findByCategoriesAndIsBoughtAndAccount(generatedArray(searcher.categories()), searcher.isBought(), accountId, paging);
        }

    }

    @Override
    public Page<Gift> getByPriceOrBought(SearcherByPriceDTO searcher, UUID accountId, Pageable paging) {

        if(searcher.isBought() == null) {
            return thisJpaRepository.findByPriceBetweenAndAccount(searcher.startPrice(), searcher.endPrice(), accountId, paging);
        } else {
            return thisJpaRepository.findByPriceBetweenAndIsBoughtAndAccount(searcher.startPrice(), searcher.endPrice(), searcher.isBought(), accountId, paging);
        }

    }

    @Override
    public Page<Gift> getByCategoriesAndPriceOrBought(SearcherByCategoriesAndPriceDTO searcher, UUID accountId, Pageable paging) {
        
        if(searcher.isBought() == null) {
            return thisJpaRepository.findByCategoriesAndPriceBetweenAndAccount(generatedArray(searcher.categories()), searcher.startPrice(), searcher.endPrice(), accountId, paging);
        } else {
            return thisJpaRepository.findByCategoriesAndPriceBetweenAndIsBoughtAndAccount(generatedArray(searcher.categories()), searcher.startPrice(), searcher.endPrice(), searcher.isBought(), accountId, paging);
        }

    }

    @Override
    public Page<Gift> getByTitleAndCategoriesOrBought(SearcherByTitleAndCategoriesDTO searcher, UUID accountId, Pageable paging) {
        
        if(searcher.isBought() == null) {
            return thisJpaRepository.findByTitleAndCategoriesAndAccount(searcher.title(), generatedArray(searcher.categories()), accountId, paging);
        } else {
            return thisJpaRepository.findByTitleAndCategoriesAndIsBoughtAndAccount(searcher.title(), generatedArray(searcher.categories()), searcher.isBought(), accountId, paging);
        }

    }

    @Override
    public Page<Gift> getByTitleAndPriceOrBought(SearcherByTitleAndPriceDTO searcher, UUID accountId, Pageable paging) {

        if(searcher.isBought() == null) {
            return thisJpaRepository.findByTitleAndPriceBetweenAndAccount(searcher.title(), searcher.startPrice(), searcher.endPrice(), accountId, paging);
        } else {
            return thisJpaRepository.findByTitleAndPriceBetweenAndIsBoughtAndAccount(searcher.title(), searcher.startPrice(), searcher.endPrice(), searcher.isBought(), accountId, paging);
        }

    }

    @Override
    public Page<Gift> getAllFilters(SearcherDTO searcher, UUID accountId, Pageable paging) {
        
        if(searcher.isBought() == null) {
            return thisJpaRepository.findByTitleAndCategoriesAndPriceBetweenAndAccount(searcher.title(), generatedArray(searcher.categories()), searcher.startPrice(), searcher.endPrice(), accountId, paging);
        } else {
            return thisJpaRepository.findByTitleAndCategoriesAndPriceBetweenAndIsBoughtAndAccount(searcher.title(), generatedArray(searcher.categories()), searcher.startPrice(), searcher.endPrice(), searcher.isBought(), accountId, paging);
        }

    }    

    private String[] generatedArray(List<CategoriesEnum> list){
        String[] out = new String[list.size()];
        for (int index = 0; index < list.size(); index++) {
            out[index] = list.get(index).toString();
        }
        return out;
    }
    
}
