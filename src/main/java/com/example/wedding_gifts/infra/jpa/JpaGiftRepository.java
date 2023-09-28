package com.example.wedding_gifts.infra.jpa;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.wedding_gifts.core.domain.model.Gift;
import com.example.wedding_gifts.core.domain.model.CategoriesEnum;
import java.util.List;
import java.math.BigDecimal;



public interface JpaGiftRepository extends JpaRepository<Gift, UUID> {
    
    public List<Gift> findByBought(boolean bought);

    public List<Gift> findByTitle(String title);
    
    public List<Gift> findByTitleAndBought(String title, boolean bought);

    public List<Gift> findByCategories(List<CategoriesEnum> categories);

    public List<Gift> findByCategoriesAndBought(List<CategoriesEnum> categories, boolean bought);

    public List<Gift> findByPriceBetween(BigDecimal start, BigDecimal end);

    public List<Gift> findByPriceBetweenAndBought(BigDecimal start, BigDecimal end, boolean bought);
    
}
