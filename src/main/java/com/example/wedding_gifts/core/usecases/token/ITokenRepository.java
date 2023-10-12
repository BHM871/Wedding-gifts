package com.example.wedding_gifts.core.usecases.token;

import com.example.wedding_gifts.core.domain.dtos.image.SaveImageDTO;
import com.example.wedding_gifts.core.domain.dtos.token.ValidTokenDTO;

public interface ITokenRepository {

    public String saveToken(SaveImageDTO tokenDto) throws Exception;

    public String getToken(ValidTokenDTO validToken) throws Exception;

    public void deleteToken(String token) throws Exception;
    
}
