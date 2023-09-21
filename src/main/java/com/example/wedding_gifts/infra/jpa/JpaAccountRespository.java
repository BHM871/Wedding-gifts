package com.example.wedding_gifts.infra.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.wedding_gifts.core.domain.model.Account;
import java.util.Optional;


public interface JpaAccountRespository extends JpaRepository<Account, String> {

    public Optional<Account> findByBrideAndGroom(String brideAndGroom);
}
