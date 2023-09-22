package com.example.wedding_gifts.application.services;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.wedding_gifts.core.domain.dtos.account.CreateAccountDTO;
import com.example.wedding_gifts.core.domain.dtos.account.LoginDTO;
import com.example.wedding_gifts.core.domain.dtos.account.UpdateAccountDTO;
import com.example.wedding_gifts.core.domain.model.Account;
import com.example.wedding_gifts.core.usecases.AccountRepository;
import com.example.wedding_gifts.core.usecases.AccountUseCase;

@Service
public class AccountServicesImpl implements AccountUseCase {

    @Autowired
    AccountRepository repository;

    @Override
    public Account createAccount(CreateAccountDTO account) throws Exception {
        validData(account);
        return repository.createAccount(account);
    }

    @Override
    public String login(LoginDTO login) throws Exception {
        //TODO: Implements Spring Security
        throw new UnsupportedOperationException("Uniplemented method 'login'");
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

    private void validData(CreateAccountDTO data) throws Exception{
        String invalid = "Some value is invalid";
        String isNull = "Some value is null";
        
        if(data.brideGroom() == null || data.brideGroom().isEmpty()) throw new Exception(isNull);
        if(data.firstName() == null || data.firstName().isEmpty()) throw new Exception(isNull);
        if(data.lastName() == null || data.lastName().isEmpty()) throw new Exception(isNull);
        if(data.email() == null || data.email().isEmpty()) throw new Exception(isNull);
        if(data.password() == null || data.password().isEmpty()) throw new Exception(isNull);
        if(data.pixKey() == null || data.pixKey().isEmpty()) throw new Exception(isNull);
        if(data.firstName() == null || data.firstName().isEmpty()) throw new Exception(isNull);
        
        if(data.brideGroom().length() < 3) throw new Exception(invalid);
        if(data.firstName().length() < 3) throw new Exception(invalid);
        if(data.lastName().length() < 3) throw new Exception(invalid);
        if(data.email().length() < 13) throw new Exception(invalid);
        if(data.password().length() < 8) throw new Exception(invalid);
        if(data.pixKey().length() < 10) throw new Exception(invalid);
   
    }
    
}
