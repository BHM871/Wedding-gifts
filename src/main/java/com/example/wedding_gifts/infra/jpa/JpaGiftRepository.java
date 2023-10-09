package com.example.wedding_gifts.infra.jpa;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.wedding_gifts.core.domain.model.Gift;
import java.util.List;
import java.math.BigDecimal;


public interface JpaGiftRepository extends JpaRepository<Gift, UUID> {

    @Query(nativeQuery = true, value = "SELECT * " +
                                        "FROM tb_gift " +
                                        "WHERE account_id = :account")
    public List<Gift> findAllByAccount(@Param("account") UUID account);
    
    @Query(nativeQuery = true, value = "SELECT * " +
                                        "FROM tb_gift " +
                                        "WHERE is_bought = :bought " + 
                                            "AND account_id = :account")
    public List<Gift> findByIsBoughtAndAccount(Boolean bought, @Param("account") UUID account);

    @Query(nativeQuery = true, value = "SELECT * " +
                                        "FROM tb_gift " +
                                        "WHERE UPPER(title) = UPPER(:title) " + 
                                            "AND account_id = :account")
    public List<Gift> findByTitleAndAccount(String title, @Param("account") UUID account);
    
    @Query(nativeQuery = true, value = "SELECT * " +
                                        "FROM tb_gift " +
                                        "WHERE UPPER(title) = UPPER(:title) " + 
                                            "AND is_bought = :bought " + 
                                            "AND account_id = :account")
    public List<Gift> findByTitleAndIsBoughtAndAccount(String title, Boolean bought, @Param("account") UUID account);

    @Query(nativeQuery = true, value = "SELECT * " +
                                        "FROM tb_gift " +
                                        "WHERE :categories = ANY(categories) " + 
                                            "AND account_id = :account")
    public List<Gift> findByCategoriesAndAccount(@Param("categories") String categories, @Param("account") UUID account);

    @Query(nativeQuery = true, value = "SELECT * " +
                                        "FROM tb_gift " +
                                        "WHERE :categories = ANY(categories) " + 
                                            "AND is_bought = :bought " + 
                                            "AND account_id = :account")
    public List<Gift> findByCategoriesAndIsBoughtAndAccount(@Param("categories") String categories, Boolean bought, @Param("account") UUID account);

    @Query(nativeQuery = true, value = "SELECT * " +
                                        "FROM tb_gift " +
                                        "WHERE price >= :start " +
                                            "AND price <= :end " + 
                                            "AND account_id = :account")
    public List<Gift> findByPriceBetweenAndAccount(BigDecimal start, BigDecimal end, @Param("account") UUID account);

    @Query(nativeQuery = true, value = "SELECT * " +
                                        "FROM tb_gift " +
                                        "WHERE price >= :start " +
                                            "AND price <= :end " +
                                            "AND is_bought = :bought" +
                                            "AND account_id = :account")
    public List<Gift> findByPriceBetweenAndIsBoughtAndAccount(BigDecimal start, BigDecimal end, Boolean bought, @Param("account") UUID account);

    @Query(nativeQuery = true, value = "SELECT * " +
                                        "FROM tb_gift " +
                                        "WHERE UPPER(title) = UPPER(:title) " + 
                                            "AND :categories = ANY(categories) " + 
                                            "AND account_id = :account")
    public List<Gift> findByTitleAndCategoriesAndAccount(String title, @Param("categories")String categories, @Param("account") UUID account);

    @Query(nativeQuery = true, value = "SELECT * " +
                                        "FROM tb_gift " +
                                        "WHERE UPPER(title) = UPPER(:title) " + 
                                            "AND :categories = ANY(categories) " + 
                                            "AND is_bought = :bought " + 
                                            "AND account_id = :account")
    public List<Gift> findByTitleAndCategoriesAndIsBoughtAndAccount(String title, @Param("categories") String categories, Boolean bought, @Param("account") UUID account);

    @Query(nativeQuery = true, value = "SELECT * " +
                                        "FROM tb_gift " +
                                        "WHERE UPPER(title) = UPPER(:title) " + 
                                            "AND price >= :start " + 
                                            "AND price <= :end " + 
                                            "AND account_id = :account")
    public List<Gift> findByTitleAndPriceBetweenAndAccount(String title, BigDecimal start, BigDecimal end, @Param("account") UUID account);

    @Query(nativeQuery = true, value = "SELECT * " +
                                        "FROM tb_gift " +
                                        "WHERE UPPER(title) = UPPER(:title) " + 
                                            "AND price >= :start " + 
                                            "AND price <= :end " + 
                                            "AND is_bought = :bought " + 
                                            "AND account_id = :account")
    public List<Gift> findByTitleAndPriceBetweenAndIsBoughtAndAccount(String title, BigDecimal start, BigDecimal end, Boolean bought, @Param("account") UUID account);

    @Query(nativeQuery = true, value = "SELECT * " +
                                        "FROM tb_gift " +
                                        "WHERE :categories = ANY(categories) " + 
                                            "AND price >= :start " + 
                                            "AND price <= :end " + 
                                            "AND account_id = :account")
    public List<Gift> findByCategoriesAndPriceBetweenAndAccount(@Param("categories") String categories, BigDecimal start, BigDecimal end, @Param("account") UUID account);

    @Query(nativeQuery = true, value = "SELECT * " +
                                        "FROM tb_gift " +
                                        "WHERE :categories = ANY(categories) " + 
                                            "AND price >= :start " + 
                                            "AND price <= :end " + 
                                            "AND is_bought = :bought " + 
                                            "AND account_id = :account")
    public List<Gift> findByCategoriesAndPriceBetweenAndIsBoughtAndAccount(@Param("categories") String categories, BigDecimal start, BigDecimal end, Boolean bought, @Param("account") UUID account);
    
    @Query(nativeQuery = true, value = "SELECT * " +
                                        "FROM tb_gift " +
                                        "WHERE UPPER(title) = UPPER(:title) " + 
                                            "AND :categories = ANY(categories) " + 
                                            "AND price >= :start " + 
                                            "AND price <= :end " + 
                                            "AND account_id = :account")
    public List<Gift> findByTitleAndCategoriesAndPriceBetweenAndAccount(String title, @Param("categories") String categories, BigDecimal start, BigDecimal end, @Param("account") UUID account);
    
    @Query(nativeQuery = true, value = "SELECT * " +
                                        "FROM tb_gift " +
                                        "WHERE UPPER(title) = UPPER(:title) " + 
                                            "AND :categories = ANY(categories) " + 
                                            "AND price >= :start " + 
                                            "AND price <= :end " + 
                                            "AND is_bought = :bought " + 
                                            "AND account_id = :account")
    public List<Gift> findByTitleAndCategoriesAndPriceBetweenAndIsBoughtAndAccount(String title, @Param("categories") String categories, BigDecimal start, BigDecimal end, Boolean bought, @Param("account") UUID account);

    @Query(nativeQuery = true, value = "DELETE " +
                                        "FROM tb_gift " +
                                        "WHERE  account_id = :account")
    public void deleteAllByAccount(@Param("account") UUID account); 

}
