package com.example.wedding_gifts_api.infra.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

import com.example.wedding_gifts_api.core.domain.model.Account;
import java.util.Optional;
import java.util.UUID;

public interface JpaAccountRespository extends JpaRepository<Account, UUID> {

    public Optional<Account> findByBrideGroom(String brideGroom);

    public UserDetails findByEmail(String email);

}
