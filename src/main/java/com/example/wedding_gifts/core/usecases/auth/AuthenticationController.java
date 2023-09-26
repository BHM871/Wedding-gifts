package com.example.wedding_gifts.core.usecases.auth;

import org.springframework.http.ResponseEntity;

import com.example.wedding_gifts.core.domain.dtos.account.CreateAccountDTO;
import com.example.wedding_gifts.core.domain.dtos.account.LoginDTO;
import com.example.wedding_gifts.core.domain.model.Account;

public interface AuthenticationController {

    public ResponseEntity<String> login(LoginDTO login) throws Exception;

    public ResponseEntity<Account> register(CreateAccountDTO account) throws Exception;
    
}
