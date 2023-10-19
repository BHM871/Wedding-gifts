package com.example.wedding_gifts.core.usecases.token;

import java.util.UUID;

import com.example.wedding_gifts.core.domain.dtos.token.SaveTokenDTO;

public interface ITokenRepository {

    public String saveToken(SaveTokenDTO tokenDto) throws Exception;

    public String getToken(String token) throws Exception;

    public String getTokenByAccount(UUID accountId) throws Exception;

    public void deleteToken(String token);
    
    public void deleteTokenByAccount(UUID accountId);

}
