package com.example.wedding_gifts_api.core.usecases.token;

import java.util.UUID;

import com.example.wedding_gifts_api.core.domain.model.Token;
import com.example.wedding_gifts_api.infra.dtos.token.SaveTokenDTO;

public interface ITokenUseCase {

    public Token saveToken(SaveTokenDTO tokenDTO) throws Exception;

    public Token validateToken(String token) throws Exception;

    public Token getTokenByAccount(UUID accounId) throws Exception;

    public void deleteToken(String token);

    public void deleteTokenByAccount(UUID accountId);
    
}
