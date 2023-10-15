package com.example.wedding_gifts.application.services;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.wedding_gifts.core.domain.dtos.account.CreateAccountDTO;
import com.example.wedding_gifts.core.domain.dtos.account.UpdateAccountDTO;
import com.example.wedding_gifts.core.domain.model.Account;
import com.example.wedding_gifts.core.usecases.account.IAccountRepository;
import com.example.wedding_gifts.core.usecases.account.IAccountUseCase;
import com.example.wedding_gifts.core.usecases.gift.IGiftUseCase;
import com.example.wedding_gifts.core.usecases.token.ITokenUseCase;

@Service
public class AccountServices implements IAccountUseCase {

    @Autowired
    private IAccountRepository repository;
    @Autowired
    private IGiftUseCase giftServices;
    @Autowired
    private ITokenUseCase tokenService;

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
        giftServices.deleteAllByAccount(id);
        tokenService.deleteTokenByAccount(id);
        repository.deleteAccount(id);
    }
    
}
