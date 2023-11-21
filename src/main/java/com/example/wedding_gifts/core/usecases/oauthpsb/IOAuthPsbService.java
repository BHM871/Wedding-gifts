package com.example.wedding_gifts.core.usecases.oauthpsb;

import com.example.wedding_gifts.core.domain.model.OAuthPsb;

public interface IOAuthPsbService {
    
    public OAuthPsb createOAuth(String token) throws Exception;

    public OAuthPsb getOAuth();

    public OAuthPsb updateOAuth() throws Exception;

    public void deleteOAuth();

}
