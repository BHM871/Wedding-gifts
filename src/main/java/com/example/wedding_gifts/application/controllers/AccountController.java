
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

import com.example.wedding_gifts.core.domain.dtos.account.AccountResponseAccountDTO;
import com.example.wedding_gifts.core.domain.dtos.account.AccountResponseIdDTO;
import com.example.wedding_gifts.core.domain.dtos.account.UpdateAccountDTO;
import com.example.wedding_gifts.core.domain.dtos.commun.MessageDTO;
import com.example.wedding_gifts.core.domain.exceptions.common.MyException;
import com.example.wedding_gifts.core.domain.model.Account;
import com.example.wedding_gifts.core.usecases.account.IAccountController;
import com.example.wedding_gifts.core.usecases.account.IAccountUseCase;

@RestController
@RequestMapping("/account")
public class AccountController implements IAccountController {

    @Autowired
    private IAccountUseCase services;

    @Override
    @GetMapping("/begin{brideGroom}")
    public ResponseEntity<AccountResponseIdDTO> gifterBegin(
        @RequestParam String brideGroom
    ) throws Exception {
        try{
            UUID id = services.verificAccountForGifter(brideGroom);
            return ResponseEntity.ok(new AccountResponseIdDTO(id));
        } catch (MyException e){
            e.setPath("/account/begin");
            throw e;
        }
    }

    @Override
    @GetMapping("/account{id}")
    public ResponseEntity<AccountResponseAccountDTO> getAccountById(
        @RequestParam UUID id
    ) throws Exception {
        try{
            Account account = services.getAccountById(id);

            AccountResponseAccountDTO accountResponse = new AccountResponseAccountDTO(
                account.getId(), 
                account.getBrideGroom(), 
                account.getWeddingDate(), 
                account.getFirstName(), 
                account.getLastName(), 
                account.getEmail());

            return ResponseEntity.ok(accountResponse);
        } catch (MyException e){
            e.setPath("/account/account");
            throw e;
        }
    }

    @Override
    @PutMapping("/update{id}")
    public ResponseEntity<AccountResponseAccountDTO> updateAccount(
        @RequestBody UpdateAccountDTO account,
        @RequestParam UUID id
    ) throws Exception {
        try{
            Account upAccount = services.updateAccount(account, id);

            AccountResponseAccountDTO accountResponse = new AccountResponseAccountDTO(
                upAccount.getId(), 
                upAccount.getBrideGroom(), 
                upAccount.getWeddingDate(), 
                upAccount.getFirstName(), 
                upAccount.getLastName(), 
                upAccount.getEmail());

            return ResponseEntity.ok(accountResponse);
        } catch (MyException e){
            e.setPath("/account/update");
            throw e;
        }
    }

    @Override
    @DeleteMapping("/delete{id}")
    public ResponseEntity<MessageDTO> deleteAccount(
        @RequestParam UUID id
    ) throws Exception {
        try{
            services.deleteAccount(id);
            return ResponseEntity.status(HttpStatus.OK).body(new MessageDTO("successfully"));
        } catch (MyException e){
            e.setPath("/account/delete");
            throw e;
        }
    }
    
}