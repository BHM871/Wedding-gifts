package com.example.wedding_gifts.adapters.security;

import java.util.UUID;

import com.example.wedding_gifts.core.domain.model.Account;

public interface TokenManagerAdapter {

    public String generatorToken(Account account) throws Exception;

    public String validateToken(String token);

    public void validateSessionId(String token, UUID pathVariableId) throws Exception;
}