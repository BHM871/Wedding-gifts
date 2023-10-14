package com.example.wedding_gifts.core.usecases.token;

import java.util.UUID;

import com.example.wedding_gifts.core.domain.dtos.token.SaveTokenDTO;
import com.example.wedding_gifts.core.domain.dtos.token.ValidTokenDTO;

public interface ITokenRepository {

    public String saveToken(SaveTokenDTO tokenDto) throws Exception;

    public String getToken(ValidTokenDTO validToken) throws Exception;

    public void deleteToken(String token) throws Exception;
    
    public void deleteTokenByAccount(UUID accountId) throws Exception;

}
