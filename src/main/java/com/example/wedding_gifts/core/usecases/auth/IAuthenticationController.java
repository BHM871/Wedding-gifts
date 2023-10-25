package com.example.wedding_gifts.core.usecases.auth;

import org.springframework.http.ResponseEntity;

import com.example.wedding_gifts.core.domain.dtos.account.AccountResponseAccountDTO;
import com.example.wedding_gifts.core.domain.dtos.account.CreateAccountDTO;
import com.example.wedding_gifts.core.domain.dtos.account.LoginDTO;
import com.example.wedding_gifts.core.domain.dtos.authentication.AuthenticationResponseDTO;
import com.example.wedding_gifts.core.domain.dtos.commun.MessageDTO;

public interface IAuthenticationController {

    public ResponseEntity<AuthenticationResponseDTO> login(LoginDTO login) throws Exception;

    public ResponseEntity<MessageDTO> logout(String token);

    public ResponseEntity<AccountResponseAccountDTO> register(CreateAccountDTO account) throws Exception;
    
}
