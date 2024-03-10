package com.example.wedding_gifts_api.core.usecases.token;

import java.util.UUID;

import com.example.wedding_gifts_api.infra.dtos.token.SaveTokenDTO;

public interface ITokenRepository {

    public String saveToken(SaveTokenDTO tokenDTO) throws Exception;

    public String getToken(String token) throws Exception;

    public String getTokenByAccount(UUID accountId) throws Exception;

    public void deleteToken(String token);
    
    public void deleteTokenByAccount(UUID accountId);

}
