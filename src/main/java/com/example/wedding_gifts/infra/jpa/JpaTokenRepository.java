package com.example.wedding_gifts.infra.jpa;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.wedding_gifts.core.domain.model.Token;

public interface JpaTokenRepository extends JpaRepository<Token, UUID> {

    public Optional<Token> findByToken(String token);
    
}
