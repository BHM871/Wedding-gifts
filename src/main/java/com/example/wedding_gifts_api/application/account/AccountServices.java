package com.example.wedding_gifts_api.application.account;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.wedding_gifts_api.core.domain.model.Account;
import com.example.wedding_gifts_api.core.usecases.account.IAccountRepository;
import com.example.wedding_gifts_api.core.usecases.account.IAccountUseCase;
import com.example.wedding_gifts_api.core.usecases.gift.IGiftUseCase;
import com.example.wedding_gifts_api.core.usecases.token.ITokenUseCase;
import com.example.wedding_gifts_api.infra.dtos.account.CreateAccountDTO;
import com.example.wedding_gifts_api.infra.dtos.account.UpdateAccountDTO;

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
        return repository.verificForGifter(brideGroom);
    }

    @Override
    public Account getAccountById(UUID id) throws Exception {
        return repository.getAccountById(id);
    }

    @Override
    public Account updateAccount(UUID account, UpdateAccountDTO update) throws Exception {
        return repository.updateAccount(account, update);
    }

    @Override
    public void deleteAccount(UUID id) throws Exception {
        giftServices.deleteAllByAccount(id);
        tokenService.deleteTokenByAccount(id);
        repository.deleteAccount(id);
    }
    
}
