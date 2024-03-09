package com.example.wedding_gifts.core.usecases.auth;

import org.springframework.http.ResponseEntity;

import com.example.wedding_gifts.infra.dtos.account.AccountResponseAccountDTO;
import com.example.wedding_gifts.infra.dtos.account.CreateAccountDTO;
import com.example.wedding_gifts.infra.dtos.account.LoginDTO;
import com.example.wedding_gifts.infra.dtos.authentication.AuthenticationResponseDTO;
import com.example.wedding_gifts.infra.dtos.commun.MessageDTO;

public interface IAuthenticationController {

    public ResponseEntity<AuthenticationResponseDTO> login(LoginDTO login) throws Exception;

    public ResponseEntity<MessageDTO> logout(String token) throws Exception;

    public ResponseEntity<AccountResponseAccountDTO> register(CreateAccountDTO account) throws Exception;
    
}
