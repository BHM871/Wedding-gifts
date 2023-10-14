package com.example.wedding_gifts.application.repositories;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.example.wedding_gifts.core.domain.dtos.token.SaveTokenDTO;
import com.example.wedding_gifts.core.domain.dtos.token.ValidTokenDTO;
import com.example.wedding_gifts.core.domain.model.Token;
import com.example.wedding_gifts.core.usecases.token.ITokenRepository;
import com.example.wedding_gifts.infra.jpa.JpaTokenRepository;

@Repository
public class TokenRepository implements ITokenRepository {

    @Autowired
    private JpaTokenRepository thisJpaRepository;

    @Override
    public String saveToken(SaveTokenDTO tokenDto) throws Exception {
        Optional<Token> oldToken = thisJpaRepository.findByAccount(tokenDto.accountId());

        if(oldToken.isPresent() && oldToken.get().getLimitHour().isBefore(LocalDateTime.now())) 
            deleteToken(oldToken.get().getToken());
        else if(oldToken.isPresent())
            return oldToken.get().getToken();

        Token newToken = new Token(tokenDto);

        return thisJpaRepository.save(newToken).getToken();
    }

    @Override
    public String getToken(ValidTokenDTO validToken) throws Exception {
        Token token = thisJpaRepository.findByToken(validToken.token()).orElseThrow(() -> new Exception("Token not found"));

        int compared = token.getAccount().getId()
                            .compareTo(validToken.accountId());
        if(compared != 0) throw new Exception("Token is not your");

        return token.getToken();
    }

    @Override
    public void deleteToken(String token) throws Exception {
        Token tokenForDelete = thisJpaRepository.findByToken(token).orElseThrow(() -> new Exception("Token not found"));

        thisJpaRepository.delete(tokenForDelete);
    }
    
}