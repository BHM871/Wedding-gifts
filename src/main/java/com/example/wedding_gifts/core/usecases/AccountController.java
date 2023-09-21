package com.example.wedding_gifts.core.usecases;

import org.springframework.http.ResponseEntity;

import com.example.wedding_gifts.core.domain.dtos.account.CreateAccountDTO;
import com.example.wedding_gifts.core.domain.dtos.account.LoginDTO;
import com.example.wedding_gifts.core.domain.dtos.account.UpdateAccountDTO;
import com.example.wedding_gifts.core.domain.model.Account;

public interface AccountController {
    
    public ResponseEntity<Account> createAccount(CreateAccountDTO account) throws Exception;

    public ResponseEntity<String> login(LoginDTO login) throws Exception;

    public ResponseEntity<String> gifterBegin(String brideAndGroom) throws Exception;

    public ResponseEntity<Account> getAccountById(String id) throws Exception;

    public ResponseEntity<Account> updateAccount(UpdateAccountDTO account) throws Exception;

    public ResponseEntity<String> deleteAccount(String id) throws Exception;
}
