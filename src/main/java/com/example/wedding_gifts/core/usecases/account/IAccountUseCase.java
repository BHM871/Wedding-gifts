package com.example.wedding_gifts.core.usecases.account;

import java.util.UUID;

import com.example.wedding_gifts.core.domain.dtos.account.AccountResponseAccountDTO;
import com.example.wedding_gifts.core.domain.dtos.account.CreateAccountDTO;
import com.example.wedding_gifts.core.domain.dtos.account.UpdateAccountDTO;

public interface IAccountUseCase {

    public AccountResponseAccountDTO createAccount(CreateAccountDTO account) throws Exception;

    public UUID verificAccountForGifter(String brideGroom) throws Exception;

    public AccountResponseAccountDTO getAccountById(UUID id) throws Exception;

    public AccountResponseAccountDTO updateAccount(UpdateAccountDTO account, UUID id) throws Exception;

    public void deleteAccount(UUID id) throws Exception;
    
}
