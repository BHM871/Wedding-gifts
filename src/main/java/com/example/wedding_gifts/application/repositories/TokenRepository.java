package com.example.wedding_gifts.application.repositories;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.example.wedding_gifts.core.domain.dtos.token.SaveTokenDTO;
import com.example.wedding_gifts.core.domain.model.Account;
import com.example.wedding_gifts.core.domain.model.Token;
import com.example.wedding_gifts.core.usecases.account.IAccountRepository;
import com.example.wedding_gifts.core.usecases.token.ITokenRepository;
import com.example.wedding_gifts.infra.jpa.JpaTokenRepository;

@Repository
public class TokenRepository implements ITokenRepository {

    @Autowired
    private JpaTokenRepository thisJpaRepository;
    @Autowired
    private IAccountRepository accountRepository;

    @Override
    public String saveToken(SaveTokenDTO tokenDto) throws Exception {
        Optional<Token> oldToken = thisJpaRepository.findByAccount(tokenDto.accountId());

        if(oldToken.isPresent() && oldToken.get().getLimitHour().isBefore(LocalDateTime.now())) 
            deleteToken(oldToken.get().getToken());
        else if(oldToken.isPresent())
            return oldToken.get().getToken();

        Token newToken = new Token(tokenDto);
        Account account = accountRepository.getAccountById(tokenDto.accountId());

        newToken.setAccount(account);

        return thisJpaRepository.save(newToken).getToken();
    }

    @Override
    public String getToken(String token) throws Exception {
        Token oldToken = thisJpaRepository.findByToken(token).orElseThrow(() -> new Exception("Token not found"));

        if(oldToken.getLimitHour().isBefore(LocalDateTime.now())) return null;

        return token;
    }

    @Override
    public String getTokenByAccount(UUID accountId) {
        Optional<Token> token = thisJpaRepository.findByAccount(accountId);

        if(token.isPresent()) return token.get().getToken();
        else return null;
    }

    @Override
    public void deleteToken(String token) throws Exception {
        Token tokenForDelete = thisJpaRepository.findByToken(token).orElseThrow(() -> new Exception("Token not found"));

        thisJpaRepository.delete(tokenForDelete);
    }

    @Override
    public void deleteTokenByAccount(UUID accountId) throws Exception {
        Token token = thisJpaRepository.findByAccount(accountId).orElseThrow(() -> new Exception("Token not found"));

        thisJpaRepository.delete(token);
    }
    
}