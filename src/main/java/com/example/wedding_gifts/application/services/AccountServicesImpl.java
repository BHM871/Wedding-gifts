package com.example.wedding_gifts.application.services;

import java.util.Date;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.wedding_gifts.core.domain.dtos.account.CreateAccountDTO;
import com.example.wedding_gifts.core.domain.dtos.account.UpdateAccountDTO;
import com.example.wedding_gifts.core.domain.model.Account;
import com.example.wedding_gifts.core.usecases.account.AccountRepository;
import com.example.wedding_gifts.core.usecases.account.AccountUseCase;

@Service
public class AccountServicesImpl implements AccountUseCase {

    @Autowired
    AccountRepository repository;

    @Override
    public Account createAccount(CreateAccountDTO account) throws Exception {
        return repository.createAccount(account);
    }

    @Override
    public UUID verificAccountForGifter(String brideGroom) throws Exception {
        if(brideGroom.length() < 3) throw new Exception("Value is invalid");

        return repository.verificForGifter(brideGroom);
    }

    @Override
    public Account getAccountById(UUID id) throws Exception {
        return repository.getAccountById(id);
    }

    @Override
    public Account updateAccount(UpdateAccountDTO account, UUID id) throws Exception {
        return repository.updateAccount(account, id);
    }

    @Override
    public void deleteAccount(UUID id) throws Exception {
        repository.deleteAccount(id);
    }
    
}
