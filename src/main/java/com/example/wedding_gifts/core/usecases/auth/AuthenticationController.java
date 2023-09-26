package com.example.wedding_gifts.core.usecases.auth;

import org.springframework.http.ResponseEntity;

import com.example.wedding_gifts.core.domain.dtos.account.CreateAccountDTO;
import com.example.wedding_gifts.core.domain.dtos.account.LoginDTO;

public interface AuthenticationController {

    public ResponseEntity login(LoginDTO login) throws Exception;

    public ResponseEntity register(CreateAccountDTO account) throws Exception;
    
}
