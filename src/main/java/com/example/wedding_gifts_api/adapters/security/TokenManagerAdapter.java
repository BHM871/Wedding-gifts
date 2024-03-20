package com.example.wedding_gifts_api.adapters.security;

import java.util.UUID;

import com.example.wedding_gifts_api.core.domain.model.Account;
import com.example.wedding_gifts_api.core.domain.model.Token;

public interface TokenManagerAdapter {

    public Token generatorToken(Account account) throws Exception;

    public String validateToken(String token);

    public void validateSessionId(String token, UUID pathVariableId) throws Exception;
}