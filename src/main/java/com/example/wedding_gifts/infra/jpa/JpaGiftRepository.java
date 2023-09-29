package com.example.wedding_gifts.infra.jpa;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.wedding_gifts.core.domain.model.Gift;
import com.example.wedding_gifts.core.domain.model.CategoriesEnum;
import java.util.List;
import java.math.BigDecimal;



public interface JpaGiftRepository extends JpaRepository<Gift, UUID> {
    
    public List<Gift> findByIsBought(Boolean bought);

    public List<Gift> findByTitle(String title);
    
    public List<Gift> findByTitleAndIsBought(String title, Boolean bought);

    public List<Gift> findByCategories(List<CategoriesEnum> categories);

    public List<Gift> findByCategoriesAndIsBought(List<CategoriesEnum> categories, Boolean bought);

    public List<Gift> findByPriceBetween(BigDecimal start, BigDecimal end);

    public List<Gift> findByPriceBetweenAndIsBought(BigDecimal start, BigDecimal end, Boolean bought);

    public List<Gift> findByTitleAndCategories(String title, List<CategoriesEnum> categories);

    public List<Gift> findByTitleAndCategoriesAndIsBought(String title, List<CategoriesEnum> categories, Boolean bought);

    public List<Gift> findByTitleAndPriceBetween(String title, BigDecimal start, BigDecimal end);

    public List<Gift> findByTitleAndPriceBetweenAndIsBought(String title, BigDecimal start, BigDecimal end, Boolean bought);

    public List<Gift> findByCategoriesAndPriceBetween(List<CategoriesEnum> categories, BigDecimal start, BigDecimal end);

    public List<Gift> findByCategoriesAndPriceBetweenAndIsBought(List<CategoriesEnum> categories, BigDecimal start, BigDecimal end, Boolean bought);
    
    public List<Gift> findByTitleAndCategoriesAndPriceBetween(String title, List<CategoriesEnum> categories, BigDecimal start, BigDecimal end);
    
    public List<Gift> findByTitleAndCategoriesAndPriceBetweenAndIsBought(String title, List<CategoriesEnum> categories, BigDecimal start, BigDecimal end, Boolean bought);
}
