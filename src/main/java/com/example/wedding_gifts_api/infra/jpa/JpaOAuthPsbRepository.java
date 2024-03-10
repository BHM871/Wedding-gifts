package com.example.wedding_gifts_api.infra.jpa;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.wedding_gifts_api.core.domain.model.OAuthPsb;


public interface JpaOAuthPsbRepository extends JpaRepository<OAuthPsb, UUID>{
    
}
