package com.example.wedding_gifts.core.usecases.token;

import java.util.UUID;

import com.example.wedding_gifts.core.domain.dtos.token.SaveTokenDTO;

public interface ITokenUseCase {

    public String saveToken(SaveTokenDTO tokenDTO) throws Exception;

    public String validateToken(String token) throws Exception;

    public void deleteToken(String token) throws Exception;

    public void deleteTokenByAccount(UUID accountId) throws Exception;
    
}
