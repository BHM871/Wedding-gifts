package com.example.wedding_gifts.core.usecases.oauthpsb;

import com.example.wedding_gifts.core.domain.dtos.oauthpsb.CreateOAuthPsb;
import com.example.wedding_gifts.core.domain.model.OAuthPsb;

public interface IOAuthPsbRepository {
    
    public OAuthPsb saveOAuth(OAuthPsb oauth) throws Exception;

    public OAuthPsb createOAuth(CreateOAuthPsb oauth) throws Exception;

    public OAuthPsb getOAuth() throws Exception;

    public OAuthPsb updateOAuth(CreateOAuthPsb oauth) throws Exception;

    public void deleteOAuth() throws Exception;
 
}
