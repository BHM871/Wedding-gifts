package com.example.wedding_gifts.infra.jpa;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.wedding_gifts.core.domain.model.Gift;
import java.util.List;
import java.math.BigDecimal;

public interface JpaGiftRepository extends JpaRepository<Gift, UUID> {
    
    public List<Gift> findByIsBought(Boolean bought);

    @Query(nativeQuery = true, value = "SELECT * " +
                                        "FROM tb_gift " +
                                        "WHERE UPPER(title) = UPPER(:title)")
    public List<Gift> findByTitle(String title);
    
    @Query(nativeQuery = true, value = "SELECT * " +
                                        "FROM tb_gift " +
                                        "WHERE UPPER(title) = UPPER(:title) AND is_bought = :bought")
    public List<Gift> findByTitleAndIsBought(String title, Boolean bought);

    @Query(nativeQuery = true, value = "SELECT * " +
                                        "FROM tb_gift " +
                                        "WHERE :categories = ANY(categories)")
    public List<Gift> findByCategories(@Param("categories") String categories);

    @Query(nativeQuery = true, value = "SELECT * " +
                                        "FROM tb_gift " +
                                        "WHERE :categories = ANY(categories) AND is_bought = :bought")
    public List<Gift> findByCategoriesAndIsBought(@Param("categories") String categories, Boolean bought);

    public List<Gift> findByPriceBetween(BigDecimal start, BigDecimal end);

    public List<Gift> findByPriceBetweenAndIsBought(BigDecimal start, BigDecimal end, Boolean bought);

    @Query(nativeQuery = true, value = "SELECT * " +
                                        "FROM tb_gift " +
                                        "WHERE UPPER(title) = UPPER(:title) AND :categories = ANY(categories)")
    public List<Gift> findByTitleAndCategories(String title, @Param("categories")String categories);

    @Query(nativeQuery = true, value = "SELECT * " +
                                        "FROM tb_gift " +
                                        "WHERE UPPER(title) = UPPER(:title) AND :categories = ANY(categories) AND is_bought = :bought")
    public List<Gift> findByTitleAndCategoriesAndIsBought(String title, @Param("categories") String categories, Boolean bought);

    @Query(nativeQuery = true, value = "SELECT * " +
                                        "FROM tb_gift " +
                                        "WHERE UPPER(title) = UPPER(:title) AND price >= :start AND price <= :end")
    public List<Gift> findByTitleAndPriceBetween(String title, BigDecimal start, BigDecimal end);

    @Query(nativeQuery = true, value = "SELECT * " +
                                        "FROM tb_gift " +
                                        "WHERE UPPER(title) = UPPER(:title) AND price >= :start AND price <= :end AND is_bought = :bought")
    public List<Gift> findByTitleAndPriceBetweenAndIsBought(String title, BigDecimal start, BigDecimal end, Boolean bought);

    @Query(nativeQuery = true, value = "SELECT * " +
                                        "FROM tb_gift " +
                                        "WHERE :categories = ANY(categories) AND price >= :start AND price <= :end")
    public List<Gift> findByCategoriesAndPriceBetween(@Param("categories") String categories, BigDecimal start, BigDecimal end);

    @Query(nativeQuery = true, value = "SELECT * " +
                                        "FROM tb_gift " +
                                        "WHERE :categories = ANY(categories) AND price >= :start AND price <= :end AND is_bought = :bought")
    public List<Gift> findByCategoriesAndPriceBetweenAndIsBought(@Param("categories") String categories, BigDecimal start, BigDecimal end, Boolean bought);
    
    @Query(nativeQuery = true, value = "SELECT * " +
                                        "FROM tb_gift " +
                                        "WHERE UPPER(title) = UPPER(:title) AND :categories = ANY(categories) AND price >= :start AND price <= :end")
    public List<Gift> findByTitleAndCategoriesAndPriceBetween(String title, @Param("categories") String categories, BigDecimal start, BigDecimal end);
    
    @Query(nativeQuery = true, value = "SELECT * " +
                                        "FROM tb_gift " +
                                        "WHERE UPPER(title) = UPPER(:title) AND :categories = ANY(categories) AND price >= :start AND price <= :end AND is_bought = :bought")
    public List<Gift> findByTitleAndCategoriesAndPriceBetweenAndIsBought(String title, @Param("categories") String categories, BigDecimal start, BigDecimal end, Boolean bought);
}
