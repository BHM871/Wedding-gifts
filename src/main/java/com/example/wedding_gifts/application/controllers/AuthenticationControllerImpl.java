package com.example.wedding_gifts.application.controllers;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.wedding_gifts.adapters.security.TokenManager;
import com.example.wedding_gifts.core.domain.dtos.account.CreateAccountDTO;
import com.example.wedding_gifts.core.domain.dtos.account.LoginDTO;
import com.example.wedding_gifts.core.domain.dtos.authentication.AuthenticationResponseDTO;
import com.example.wedding_gifts.core.domain.model.Account;
import com.example.wedding_gifts.core.usecases.account.AccountRepository;
import com.example.wedding_gifts.core.usecases.auth.AuthenticationController;

import lombok.var;

@RestController
@RequestMapping("/auth")
public class AuthenticationControllerImpl implements AuthenticationController {

    @Autowired
    AccountRepository repository;
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    TokenManager tokenManager;

    @Override
    @PostMapping("/register")
    public ResponseEntity<Account> register(
        @RequestBody CreateAccountDTO account
    ) throws Exception {
        validData(account);

        if(repository.getByEmail(account.email()) != null) return ResponseEntity.status(HttpStatus.CONFLICT).build();

        String encrypPassword = new BCryptPasswordEncoder().encode(account.password());
        String encrypPixKey = new BCryptPasswordEncoder().encode(account.pixKey());

        CreateAccountDTO createAccount = new CreateAccountDTO(
            account.firstName(), 
            account.lastName(), 
            account.brideGroom(), 
            account.weddingDate(), 
            account.email(), 
            encrypPassword, 
            encrypPixKey
        );

        Account newAccount = repository.createAccount(createAccount);

        return ResponseEntity.status(HttpStatus.CREATED).body(newAccount);
    }

    @Override
    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponseDTO> login(
        @RequestBody LoginDTO login
    ) throws Exception {
        validData(login);

        var usernamePassword = new UsernamePasswordAuthenticationToken(login.email(), login.password());
        var auth = this.authenticationManager.authenticate(usernamePassword);

        String token = tokenManager.generatorToken((Account) auth.getPrincipal());

        if(auth.isAuthenticated()) return ResponseEntity.ok(new AuthenticationResponseDTO(token));

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    private void validData(CreateAccountDTO data) throws Exception{
        String invalid = "Some value is invalid";
        String isNull = "Some value is null";
        
        if(data.brideGroom() == null || data.brideGroom().isEmpty()) throw new Exception(isNull);
        if(data.weddingDate() == null) throw new Exception(isNull);
        if(data.firstName() == null || data.firstName().isEmpty()) throw new Exception(isNull);
        if(data.lastName() == null || data.lastName().isEmpty()) throw new Exception(isNull);
        if(data.email() == null || data.email().isEmpty()) throw new Exception(isNull);
        if(data.password() == null || data.password().isEmpty()) throw new Exception(isNull);
        if(data.pixKey() == null || data.pixKey().isEmpty()) throw new Exception(isNull);
        if(data.firstName() == null || data.firstName().isEmpty()) throw new Exception(isNull);
        
        if(data.brideGroom().length() < 3) throw new Exception(invalid);
        if(data.weddingDate().getTime() < new Date().getTime()) throw new Exception(invalid);
        if(data.firstName().length() < 3) throw new Exception(invalid);
        if(data.lastName().length() < 3) throw new Exception(invalid);
        if(data.email().length() < 13) throw new Exception(invalid);
        if(data.password().length() < 8) throw new Exception(invalid);
        if(data.pixKey().length() < 10) throw new Exception(invalid);
   
    }

    private void validData(LoginDTO data) throws Exception {
        String invalid = "Some value is invalid";
        String isNull = "Some value is null";

        if(data.email() == null || data.email().isEmpty()) throw new Exception(isNull);
        if(data.password() == null || data.password().isEmpty()) throw new Exception(isNull);
        
        if(data.email().length() < 13) throw new Exception(invalid);
        if(data.password().length() < 8) throw new Exception(invalid);
    }
    
}
