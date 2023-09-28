
package com.example.wedding_gifts.application.controllers;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.wedding_gifts.core.domain.dtos.account.AccountResponseIdDTO;
import com.example.wedding_gifts.core.domain.dtos.account.UpdateAccountDTO;
import com.example.wedding_gifts.core.domain.model.Account;
import com.example.wedding_gifts.core.usecases.account.AccountController;
import com.example.wedding_gifts.core.usecases.account.AccountUseCase;

@RestController
@RequestMapping("/account")
public class AccountControllerImpl implements AccountController {

    @Autowired
    AccountUseCase services;

    @Override
    @GetMapping("/begin{brideGroom}")
    public ResponseEntity<AccountResponseIdDTO> gifterBegin(
        @RequestParam(name = "brideGroom", required = true) String brideGroom
    ) throws Exception {
        UUID id = services.verificAccountForGifter(brideGroom);
        return ResponseEntity.ok(new AccountResponseIdDTO(id));    
    }

    @Override
    @GetMapping("/account{id}")
    public ResponseEntity<Account> getAccountById(
        @RequestParam(name = "id", required = true) UUID id
    ) throws Exception {
        return ResponseEntity.ok(services.getAccountById(id));
    }

    @Override
    @PutMapping("/update{id}")
    public ResponseEntity<Account> updateAccount(
        @RequestBody UpdateAccountDTO account,
        @RequestParam(name = "id", required = true) UUID id
    ) throws Exception {
        return ResponseEntity.ok(services.updateAccount(account, id));
    }

    @Override
    @DeleteMapping("/delete{id}")
    public ResponseEntity<String> deleteAccount(
        @RequestParam(name = "id", required = true) UUID id
    ) throws Exception {
        services.deleteAccount(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("successfully");    
    }
    
}
