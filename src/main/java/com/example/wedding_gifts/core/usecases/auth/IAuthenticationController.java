package com.example.wedding_gifts.core.usecases.auth;

import org.springframework.http.ResponseEntity;

import com.example.wedding_gifts.core.domain.dtos.account.CreateAccountDTO;
import com.example.wedding_gifts.core.domain.dtos.account.LoginDTO;
import com.example.wedding_gifts.core.domain.dtos.authentication.AuthenticationResponseDTO;
import com.example.wedding_gifts.core.domain.model.Account;

public interface IAuthenticationController {

    public ResponseEntity<AuthenticationResponseDTO> login(LoginDTO login) throws Exception;

    public ResponseEntity<Account> register(CreateAccountDTO account) throws Exception;
    
}