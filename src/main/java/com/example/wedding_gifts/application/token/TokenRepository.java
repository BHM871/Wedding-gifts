package com.example.wedding_gifts.application.token;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.example.wedding_gifts.common.MyZone;
import com.example.wedding_gifts.core.domain.dtos.token.SaveTokenDTO;
import com.example.wedding_gifts.core.domain.exceptions.token.TokenExecutionException;
import com.example.wedding_gifts.core.domain.model.Account;
import com.example.wedding_gifts.core.domain.model.Token;
import com.example.wedding_gifts.core.usecases.account.IAccountRepository;
import com.example.wedding_gifts.core.usecases.token.ITokenRepository;
import com.example.wedding_gifts.infra.jpa.JpaTokenRepository;

@Repository
public class TokenRepository implements ITokenRepository {

    @Autowired
    private JpaTokenRepository jpaRepository;
    @Autowired
    private IAccountRepository accountRepository;

    @Override
    public String saveToken(SaveTokenDTO tokenDto) throws Exception {
        try{
            Optional<Token> oldToken = jpaRepository.findByAccount(tokenDto.accountId());

            if(oldToken.isPresent() && oldToken.get().getLimitHour().isBefore(LocalDateTime.now(MyZone.zoneId()))) 
                deleteToken(oldToken.get().getTokenValue());
            else if(oldToken.isPresent())
                return oldToken.get().getTokenValue();

            Token newToken = new Token(tokenDto);
            Account account = accountRepository.getAccountById(tokenDto.accountId());

            newToken.setAccount(account);

            return jpaRepository.save(newToken).getTokenValue();
        } catch (Exception e) {
            throw new TokenExecutionException("Token can't be saved", e);
        }
    }

    @Override
    public String getToken(String token) throws Exception {
        try{
            Optional<Token> oldToken = jpaRepository.findByTokenValue(token);

            if(!oldToken.isPresent()) {
                return null;
            } else if(oldToken.get().getLimitHour().isBefore(LocalDateTime.now(MyZone.zoneId()))) {
                deleteToken(oldToken.get().getTokenValue());
                return null;
            }

            return token;
        } catch (Exception e){
            throw new TokenExecutionException("Error in Token validation", e);
        }
    }

    @Override
    public String getTokenByAccount(UUID accountId) throws Exception {
        try{
            Optional<Token> token = jpaRepository.findByAccount(accountId);

            if(!token.isPresent()) {
                return null;
            } else if(token.get().getLimitHour().isBefore(LocalDateTime.now(MyZone.zoneId()))) {
                deleteToken(token.get().getTokenValue());
                return null;
            }

            return token.get().getTokenValue();
        } catch (Exception e){
            throw new TokenExecutionException("Error in Token verification");
        }
    }

    @Override
    public void deleteToken(String token) {
        Optional<Token> tokenForDelete = jpaRepository.findByTokenValue(token);

        if(tokenForDelete.isPresent()) jpaRepository.delete(tokenForDelete.get());
    }

    @Override
    public void deleteTokenByAccount(UUID accountId) {
        Optional<Token> token = jpaRepository.findByAccount(accountId);

        if(token.isPresent()) jpaRepository.delete(token.get());
    }
    
}