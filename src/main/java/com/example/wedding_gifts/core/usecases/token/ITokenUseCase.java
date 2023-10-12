package com.example.wedding_gifts.core.usecases.token;

import com.example.wedding_gifts.core.domain.dtos.token.SaveTokenDTO;
import com.example.wedding_gifts.core.domain.dtos.token.ValidTokenDTO;

public interface ITokenUseCase {

    public String saveToken(SaveTokenDTO tokenDTO) throws Exception;

    public String validateToken(ValidTokenDTO validToken) throws Exception;

    public void deleteToken(String token) throws Exception;
    
}
