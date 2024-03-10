package com.example.wedding_gifts_api.application.token;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.wedding_gifts_api.core.usecases.token.ITokenRepository;
import com.example.wedding_gifts_api.core.usecases.token.ITokenUseCase;
import com.example.wedding_gifts_api.infra.dtos.token.SaveTokenDTO;

@Service
public class TokenServices implements ITokenUseCase {

    @Autowired
    private ITokenRepository repository;

    @Override
    public String saveToken(SaveTokenDTO tokenDTO) throws Exception {
        return repository.saveToken(tokenDTO);
    }

    @Override
    public String validateToken(String token) throws Exception {
        return repository.getToken(token);
    }

    @Override
    public String getTokenByAccount(UUID accounId) throws Exception {
        return repository.getTokenByAccount(accounId);
    }

    @Override
    public void deleteToken(String token) {
        repository.deleteToken(token);
    }

    @Override
    public void deleteTokenByAccount(UUID accountId) {
        repository.deleteTokenByAccount(accountId);
    }
    
}
