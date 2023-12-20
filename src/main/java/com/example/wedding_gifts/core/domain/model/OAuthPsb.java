package com.example.wedding_gifts.core.domain.model;

import java.time.LocalDateTime;
import java.util.UUID;

import com.example.wedding_gifts.core.domain.dtos.oauthpsb.CreateOAuthPsb;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "tb_oauth_psb")
@Getter
@Setter
public class OAuthPsb {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    
    @Column(length = -1, unique = true)
    private String authToken;

    private LocalDateTime expiration;

    private String scope;

    private String tokenType;

    public OAuthPsb(
        String id,
        String token,
        Long expiration,
        String scope
    ) {
        this.id = generateId();
        this.authToken = token;
        this.expiration = LocalDateTime.now().plusSeconds(expiration);
        this.scope = scope;
    }

    public OAuthPsb(CreateOAuthPsb oauth) {

        authToken = oauth.access_token();
        expiration = LocalDateTime.now().plusSeconds(oauth.expires_in());
        scope = oauth.scope();
        tokenType = oauth.token_type();

    }

    public OAuthPsb() {
        this.id = generateId();
        this.authToken = null;
        LocalDateTime.now();
        this.scope = "";
    }

    private UUID generateId(){
        return UUID.fromString("123e4567-e89b-12d3-a456-426614174000");
    }

}