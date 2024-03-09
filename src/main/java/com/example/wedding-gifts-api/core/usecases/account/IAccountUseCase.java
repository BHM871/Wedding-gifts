package com.example.wedding_gifts.core.usecases.account;

import java.util.UUID;

import com.example.wedding_gifts.core.domain.model.Account;
import com.example.wedding_gifts.infra.dtos.account.CreateAccountDTO;
import com.example.wedding_gifts.infra.dtos.account.UpdateAccountDTO;

public interface IAccountUseCase {

    public Account createAccount(CreateAccountDTO account) throws Exception;

    public UUID verificAccountForGifter(String brideGroom) throws Exception;

    public Account getAccountById(UUID id) throws Exception;

    public Account updateAccount(UUID id, UpdateAccountDTO account) throws Exception;

    public void deleteAccount(UUID account) throws Exception;
    
}
