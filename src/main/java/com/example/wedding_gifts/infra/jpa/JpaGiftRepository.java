package com.example.wedding_gifts.infra.jpa;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.wedding_gifts.core.domain.model.Gift;
import java.util.List;
import java.math.BigDecimal;


public interface JpaGiftRepository extends JpaRepository<Gift, UUID> {
    
    @Query(nativeQuery = true, value = "SELECT DISTINCT * " +
                                        "FROM tb_gift " +
                                        "WHERE account_id = :account")
    public List<Gift> findAllByAccount(@Param("account") UUID account);
    
    @Query(nativeQuery = true, value = "SELECT DISTINCT * " +
                                        "FROM tb_gift " +
                                        "WHERE account_id = :account")
    public Page<Gift> findAllByAccount(@Param("account") UUID account, Pageable page);
    
    @Query(nativeQuery = true, value = "SELECT DISTINCT * " +
                                        "FROM tb_gift " +
                                        "WHERE is_bought = :bought " + 
                                            "AND account_id = :account")
    public Page<Gift> findByIsBoughtAndAccount(Boolean bought, @Param("account") UUID account, Pageable page);

    @Query(nativeQuery = true, value = "SELECT DISTINCT * " +
                                        "FROM tb_gift " +
                                        "WHERE UPPER(title) LIKE UPPER(:title) " + 
                                            "AND account_id = :account")
    public Page<Gift> findByTitleAndAccount(String title, @Param("account") UUID account, Pageable page);
    
    @Query(nativeQuery = true, value = "SELECT DISTINCT * " +
                                        "FROM tb_gift " +
                                        "WHERE UPPER(title) LIKE UPPER(:title) " + 
                                            "AND is_bought = :bought " + 
                                            "AND account_id = :account")
    public Page<Gift> findByTitleAndIsBoughtAndAccount(String title, Boolean bought, @Param("account") UUID account, Pageable page);

    @Query(nativeQuery = true, value = "SELECT DISTINCT * " +
                                        "FROM tb_gift " +
                                        "WHERE categories && ARRAY[:categories] " + 
                                            "AND account_id = :account")
    public Page<Gift> findByCategoriesAndAccount(@Param("categories") String[] categories, @Param("account") UUID account, Pageable page);

    @Query(nativeQuery = true, value = "SELECT DISTINCT * " +
                                        "FROM tb_gift " +
                                        "WHERE categories && ARRAY[:categories] " + 
                                            "AND is_bought = :bought " + 
                                            "AND account_id = :account")
    public Page<Gift> findByCategoriesAndIsBoughtAndAccount(@Param("categories") String[] categories, Boolean bought, @Param("account") UUID account, Pageable page);

    @Query(nativeQuery = true, value = "SELECT DISTINCT * " +
                                        "FROM tb_gift " +
                                        "WHERE price >= :start " +
                                            "AND price <= :end " + 
                                            "AND account_id = :account")
    public Page<Gift> findByPriceBetweenAndAccount(BigDecimal start, BigDecimal end, @Param("account") UUID account, Pageable page);

    @Query(nativeQuery = true, value = "SELECT DISTINCT * " +
                                        "FROM tb_gift " +
                                        "WHERE price >= :start " +
                                            "AND price <= :end " +
                                            "AND is_bought = :bought" +
                                            "AND account_id = :account")
    public Page<Gift> findByPriceBetweenAndIsBoughtAndAccount(BigDecimal start, BigDecimal end, Boolean bought, @Param("account") UUID account, Pageable page);

    @Query(nativeQuery = true, value = "SELECT DISTINCT * " +
                                        "FROM tb_gift " +
                                        "WHERE UPPER(title) LIKE UPPER(:title) " + 
                                            "AND categories && ARRAY[:categories] " + 
                                            "AND account_id = :account")
    public Page<Gift> findByTitleAndCategoriesAndAccount(String title, @Param("categories")String[] categories, @Param("account") UUID account, Pageable page);

    @Query(nativeQuery = true, value = "SELECT DISTINCT * " +
                                        "FROM tb_gift " +
                                        "WHERE UPPER(title) LIKE UPPER(:title) " + 
                                            "AND categories && ARRAY[:categories] " + 
                                            "AND is_bought = :bought " + 
                                            "AND account_id = :account")
    public Page<Gift> findByTitleAndCategoriesAndIsBoughtAndAccount(String title, @Param("categories") String[] categories, Boolean bought, @Param("account") UUID account, Pageable page);

    @Query(nativeQuery = true, value = "SELECT DISTINCT * " +
                                        "FROM tb_gift " +
                                        "WHERE UPPER(title) LIKE UPPER(:title) " + 
                                            "AND price >= :start " + 
                                            "AND price <= :end " + 
                                            "AND account_id = :account")
    public Page<Gift> findByTitleAndPriceBetweenAndAccount(String title, BigDecimal start, BigDecimal end, @Param("account") UUID account, Pageable page);

    @Query(nativeQuery = true, value = "SELECT DISTINCT * " +
                                        "FROM tb_gift " +
                                        "WHERE UPPER(title) LIKE UPPER(:title) " + 
                                            "AND price >= :start " + 
                                            "AND price <= :end " + 
                                            "AND is_bought = :bought " + 
                                            "AND account_id = :account")
    public Page<Gift> findByTitleAndPriceBetweenAndIsBoughtAndAccount(String title, BigDecimal start, BigDecimal end, Boolean bought, @Param("account") UUID account, Pageable page);

    @Query(nativeQuery = true, value = "SELECT DISTINCT * " +
                                        "FROM tb_gift " +
                                        "WHERE categories && ARRAY[:categories] " + 
                                            "AND price >= :start " + 
                                            "AND price <= :end " + 
                                            "AND account_id = :account")
    public Page<Gift> findByCategoriesAndPriceBetweenAndAccount(@Param("categories") String[] categories, BigDecimal start, BigDecimal end, @Param("account") UUID account, Pageable page);

    @Query(nativeQuery = true, value = "SELECT DISTINCT * " +
                                        "FROM tb_gift " +
                                        "WHERE categories && ARRAY[:categories] " + 
                                            "AND price >= :start " + 
                                            "AND price <= :end " + 
                                            "AND is_bought = :bought " + 
                                            "AND account_id = :account")
    public Page<Gift> findByCategoriesAndPriceBetweenAndIsBoughtAndAccount(@Param("categories") String[] categories, BigDecimal start, BigDecimal end, Boolean bought, @Param("account") UUID account, Pageable page);
    
    @Query(nativeQuery = true, value = "SELECT DISTINCT * " +
                                        "FROM tb_gift " +
                                        "WHERE UPPER(title) LIKE UPPER(:title) " + 
                                            "AND categories && ARRAY[:categories] " + 
                                            "AND price >= :start " + 
                                            "AND price <= :end " + 
                                            "AND account_id = :account")
    public Page<Gift> findByTitleAndCategoriesAndPriceBetweenAndAccount(String title, @Param("categories") String[] categories, BigDecimal start, BigDecimal end, @Param("account") UUID account, Pageable page);
    
    @Query(nativeQuery = true, value = "SELECT DISTINCT * " +
                                        "FROM tb_gift " +
                                        "WHERE UPPER(title) LIKE UPPER(:title) " + 
                                            "AND categories && ARRAY[:categories] " + 
                                            "AND price >= :start " + 
                                            "AND price <= :end " + 
                                            "AND is_bought = :bought " + 
                                            "AND account_id = :account")
    public Page<Gift> findByTitleAndCategoriesAndPriceBetweenAndIsBoughtAndAccount(String title, @Param("categories") String[] categories, BigDecimal start, BigDecimal end, Boolean bought, @Param("account") UUID account, Pageable page);

    @Query(nativeQuery = true, value = "DELETE " +
                                        "FROM tb_gift " +
                                        "WHERE  account_id = :account")
    public void deleteAllByAccount(@Param("account") UUID account); 

}
