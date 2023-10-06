package com.example.wedding_gifts.core.usecases.auth;

import org.springframework.http.ResponseEntity;

import com.example.wedding_gifts.core.domain.dtos.account.AccountResponseAccountDTO;
import com.example.wedding_gifts.core.domain.dtos.account.CreateAccountDTO;
import com.example.wedding_gifts.core.domain.dtos.account.LoginDTO;
import com.example.wedding_gifts.core.domain.dtos.authentication.AuthenticationResponseDTO;

public interface IAuthenticationController {

    public ResponseEntity<AuthenticationResponseDTO> login(LoginDTO login) throws Exception;

    public ResponseEntity<AccountResponseAccountDTO> register(CreateAccountDTO account) throws Exception;
    
}
