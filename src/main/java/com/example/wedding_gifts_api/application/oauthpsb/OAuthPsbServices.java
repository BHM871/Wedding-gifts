package com.example.wedding_gifts_api.application.oauthpsb;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.wedding_gifts_api.core.domain.model.OAuthPsb;
import com.example.wedding_gifts_api.core.usecases.oauthpsb.IOAuthPsbRepository;
import com.example.wedding_gifts_api.core.usecases.oauthpsb.IOAuthPsbUsecase;
import com.example.wedding_gifts_api.infra.dtos.oauthpsb.CreateOAuthPsb;

@Service
public class OAuthPsbServices implements IOAuthPsbUsecase {

    @Autowired
    private IOAuthPsbRepository repository;

    @Override
    public OAuthPsb createOAuth(CreateOAuthPsb oauth) throws Exception {
        return repository.createOAuth(oauth);
    }

    @Override
    public OAuthPsb getOAuth() throws Exception {
        return repository.getOAuth();
    }

    @Override
    public OAuthPsb updateOAuth(CreateOAuthPsb oauth) throws Exception {
        return repository.updateOAuth(oauth);
    }

    @Override
    public void deleteOAuth() throws Exception {
        repository.deleteOAuth();
    }
    
}
