package com.example.wedding_gifts_api.core.usecases.oauthpsb;

import com.example.wedding_gifts_api.core.domain.model.OAuthPsb;
import com.example.wedding_gifts_api.infra.dtos.oauthpsb.CreateOAuthPsb;

public interface IOAuthPsbUsecase {
    
    public OAuthPsb createOAuth(CreateOAuthPsb oauth) throws Exception;

    public OAuthPsb getOAuth() throws Exception;

    public OAuthPsb updateOAuth(CreateOAuthPsb oauth) throws Exception;

    public void deleteOAuth() throws Exception;

}
