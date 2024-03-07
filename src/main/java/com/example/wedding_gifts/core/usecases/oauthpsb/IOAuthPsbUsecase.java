package com.example.wedding_gifts.core.usecases.oauthpsb;

import com.example.wedding_gifts.core.domain.model.OAuthPsb;
import com.example.wedding_gifts.infra.dtos.oauthpsb.CreateOAuthPsb;

public interface IOAuthPsbUsecase {
    
    public OAuthPsb createOAuth(CreateOAuthPsb oauth) throws Exception;

    public OAuthPsb getOAuth() throws Exception;

    public OAuthPsb updateOAuth(CreateOAuthPsb oauth) throws Exception;

    public void deleteOAuth() throws Exception;

}
