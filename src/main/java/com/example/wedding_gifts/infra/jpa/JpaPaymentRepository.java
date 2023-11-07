package com.example.wedding_gifts.infra.jpa;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.wedding_gifts.core.domain.model.Payment;

public interface JpaPaymentRepository extends JpaRepository<Payment, UUID> {

    @Query(nativeQuery = true, value = "SELECT DISTINCT * " +
                                        "FROM tb_payment " +
                                        "WHERE account_id = :accountId")
    public Page<Payment> findByAccountId(@Param("accountId") UUID accountId, Pageable paging);

    @Query(nativeQuery = true, value = "SELECT DISTINCT * " +
                                        "FROM tb_payment " +
                                        "WHERE is_paid = :isPaid " + 
                                            "AND account_id = :accountId")
    public Page<Payment> findByIsPaidAndAccountId(Boolean isPaid, UUID accountId, Pageable paging);
  
} 
