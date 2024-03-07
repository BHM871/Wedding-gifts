package com.example.wedding_gifts.application.gift;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.example.wedding_gifts.core.domain.dtos.account.AccountResponseIdDTO;
import com.example.wedding_gifts.core.domain.dtos.gift.CreateGiftDTO;
import com.example.wedding_gifts.core.domain.dtos.gift.DeleteGiftDTO;
import com.example.wedding_gifts.core.domain.dtos.gift.GiftDTO;
import com.example.wedding_gifts.core.domain.dtos.gift.GiftResponseDTO;
import com.example.wedding_gifts.core.domain.dtos.gift.UpdateGiftDTO;
import com.example.wedding_gifts.core.domain.dtos.gift.searchers.SearcherByCategoriesAndPriceDTO;
import com.example.wedding_gifts.core.domain.dtos.gift.searchers.SearcherByCategoriesDTO;
import com.example.wedding_gifts.core.domain.dtos.gift.searchers.SearcherByPriceDTO;
import com.example.wedding_gifts.core.domain.dtos.gift.searchers.SearcherByTitleAndCategoriesDTO;
import com.example.wedding_gifts.core.domain.dtos.gift.searchers.SearcherByTitleAndPriceDTO;
import com.example.wedding_gifts.core.domain.dtos.gift.searchers.SearcherByTitleDTO;
import com.example.wedding_gifts.core.domain.dtos.gift.searchers.SearcherDTO;
import com.example.wedding_gifts.core.domain.dtos.image.ImageDTO;
import com.example.wedding_gifts.core.domain.dtos.image.ImageResponseDTO;
import com.example.wedding_gifts.core.domain.model.Gift;
import com.example.wedding_gifts.core.domain.model.Image;
import com.example.wedding_gifts.core.usecases.gift.IGiftRepository;
import com.example.wedding_gifts.core.usecases.gift.IGiftUseCase;
import com.example.wedding_gifts.core.usecases.image.IImageUseCase;

@Service
public class GiftServices implements IGiftUseCase {

    @Autowired
    private IGiftRepository repository;
    @Autowired
    private IImageUseCase imageService;
    
    @Override
    public GiftResponseDTO createGift(CreateGiftDTO gift) throws Exception {
        Gift newGift = repository.createGift(gift);

        GiftDTO newGiftDto = new GiftDTO(
            newGift.getId(), 
            newGift.getTitle(), 
            newGift.getGiftDescription(), 
            newGift.getPrice(), 
            newGift.getCategories(), 
            newGift.getIsBought(), 
            new AccountResponseIdDTO(newGift.getAccount().getId())
        );

        List<ImageResponseDTO> imagesResponse = new ArrayList<ImageResponseDTO>();
        if(gift.images() != null) {
            for(String image : gift.images()) {
                Image temp = imageService.createImage(
                    new ImageDTO(imageService.toImage(image), newGift.getId(), newGift.getAccount().getId())
                );

                imagesResponse.add(
                    new ImageResponseDTO(temp.getId(), temp.getPathImage())
                );
            }
        }
        
        return new GiftResponseDTO(newGiftDto, imagesResponse);
    }

    @Override
    public void updateGift(UpdateGiftDTO gift) throws Exception {
        repository.updateGift(gift);        
    }

    @Override
    public void deleteGift(DeleteGiftDTO deleteGift) throws Exception {

        imageService.deleteAllByGift(deleteGift.giftId());

        repository.deleteGift(deleteGift);
    }

    @Override
    public void deleteAllByAccount(UUID accountId) throws Exception {

        List<Gift> gifts = repository.getAllGifts(accountId);
        for(Gift gift : gifts) {
            imageService.deleteAllByGift(gift.getId());
        }

        repository.deleteAllByAccount(accountId);
    }

    @Override
    public Gift getById(UUID giftId) throws Exception {
        return repository.getGiftById(giftId);
    }

    @Override
    public Page<GiftResponseDTO> getAllGifts(UUID accountId, Pageable paging) throws Exception {
        Page<Gift> gifts = repository.getAllGifts(accountId, paging);

        return generatedGiftResponse(gifts, paging);
    }

    @Override
    public Page<GiftResponseDTO> getWithFilter(SearcherDTO searcher, UUID accountId, Pageable paging) throws Exception {
        Page<Gift> gifts = Page.empty();
        
        if(searcher.title() != null && searcher.categories() != null && (searcher.startPrice() != null || searcher.endPrice() != null)){ 
            gifts = repository.getAllFilters(searcherDTO(searcher), accountId, paging);
        } else 
        if(searcher.title() != null){
            if(searcher.categories() != null) {
                gifts = repository.getByTitleAndCategoriesOrBought(searcherByTitleAndCategoriesDTO(searcher), accountId, paging);
            } else 
            if(searcher.startPrice() != null || searcher.endPrice() != null) {
                gifts = repository.getByTitleAndPriceOrBought(searcherByTitleAndPriceDTO(searcher), accountId, paging);
            } else {
                gifts = repository.getByTitleOrBoutght(searcherByTitleDTO(searcher), accountId, paging);
            }

        } else 
        if(searcher.categories() != null) {
            if(searcher.startPrice() != null || searcher.endPrice() != null) {
                gifts = repository.getByCategoriesAndPriceOrBought(searcherByCategoriesAndPriceDTO(searcher), accountId, paging);
            } else {
                gifts = repository.getByCategoriesOrBought(searcherByCategoriesDTO(searcher), accountId, paging);
            }

        } else 
        if(searcher.startPrice() != null || searcher.endPrice() != null) {
            gifts = repository.getByPriceOrBought(searcherByPriceDTO(searcher), accountId, paging);
        
        } else {
            gifts = repository.getAllGifts(accountId, paging);
        }

        return generatedGiftResponse(gifts, gifts.getPageable());
    }

    private SearcherDTO searcherDTO(SearcherDTO searcher) {
        return new SearcherDTO(
            searcher.isBought(), 
            "%"+searcher.title()+"%", 
            searcher.startPrice() != null ? searcher.startPrice() : BigDecimal.ZERO, 
            searcher.endPrice() != null ? searcher.endPrice() : BigDecimal.valueOf(Double.MAX_VALUE),
            searcher.categories() 
        );
    }

    private SearcherByTitleAndCategoriesDTO searcherByTitleAndCategoriesDTO(SearcherDTO searcher) {
        return new SearcherByTitleAndCategoriesDTO(
            "%"+searcher.title()+"%", 
            searcher.categories(), 
            searcher.isBought()
        );
    }

    private SearcherByTitleAndPriceDTO searcherByTitleAndPriceDTO(SearcherDTO searcher) {
        return new SearcherByTitleAndPriceDTO(
            "%"+searcher.title()+"%", 
            searcher.startPrice() != null ? searcher.startPrice() : BigDecimal.ZERO, 
            searcher.endPrice() != null ? searcher.endPrice() : BigDecimal.valueOf(Double.MAX_VALUE),
            searcher.isBought() 
        );
    }

    private SearcherByTitleDTO searcherByTitleDTO(SearcherDTO searcher) {
        return new SearcherByTitleDTO(
            "%"+searcher.title()+"%", 
            searcher.isBought()
        );
    }

    private SearcherByCategoriesAndPriceDTO searcherByCategoriesAndPriceDTO(SearcherDTO searcher) {
        return new SearcherByCategoriesAndPriceDTO(
            searcher.categories(), 
            searcher.startPrice() != null ? searcher.startPrice() : BigDecimal.ZERO, 
            searcher.endPrice() != null ? searcher.endPrice() : BigDecimal.valueOf(Double.MAX_VALUE),
            searcher.isBought() 
        );
    }

    private SearcherByCategoriesDTO searcherByCategoriesDTO(SearcherDTO searcher) {
        return new SearcherByCategoriesDTO(
            searcher.categories(), 
            searcher.isBought()
        );
    }

    private SearcherByPriceDTO searcherByPriceDTO(SearcherDTO searcher) {
        return new SearcherByPriceDTO(
            searcher.startPrice() != null ? searcher.startPrice() : BigDecimal.ZERO, 
            searcher.endPrice() != null ? searcher.endPrice() : BigDecimal.valueOf(Double.MAX_VALUE),
            searcher.isBought() 
        );
    }

    private Page<GiftResponseDTO> generatedGiftResponse(Page<Gift> gifts, Pageable paging) throws Exception {

        List<GiftDTO> giftsDto = gifts.stream().map( gift ->
            new GiftDTO(
                gift.getId(), 
                gift.getTitle(), 
                gift.getGiftDescription(), 
                gift.getPrice(), 
                gift.getCategories(), 
                gift.getIsBought(), 
                new AccountResponseIdDTO(gift.getAccount().getId())
            )
        ).toList();
        
        List<GiftResponseDTO> giftResponseList = new ArrayList<GiftResponseDTO>();
        for(GiftDTO gift : giftsDto) {
            List<Image> images = imageService.getAllByGift(gift.id());
            List<ImageResponseDTO> imageResponse = images.stream().map(image -> new ImageResponseDTO(image.getId(), image.getPathImage())).toList(); 
            
            giftResponseList.add(new GiftResponseDTO(gift, imageResponse));
        }

        return new PageImpl<GiftResponseDTO>(giftResponseList, paging, gifts.getTotalElements());
    }
    
}
