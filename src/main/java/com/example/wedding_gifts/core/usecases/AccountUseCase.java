package com.example.wedding_gifts.core.usecases;

import java.util.UUID;

import com.example.wedding_gifts.core.domain.dtos.account.CreateAccountDTO;
import com.example.wedding_gifts.core.domain.dtos.account.LoginDTO;
import com.example.wedding_gifts.core.domain.dtos.account.UpdateAccountDTO;
import com.example.wedding_gifts.core.domain.model.Account;

public interface AccountUseCase {

    public Account createAccount(CreateAccountDTO account) throws Exception;

    public String login(LoginDTO login) throws Exception;

    public UUID verificAccountForGifter(String brideGroom) throws Exception;

    public Account getAccountById(UUID id) throws Exception;

    public Account updateAccount(UpdateAccountDTO account, UUID id) throws Exception;

    public void deleteAccount(UUID id) throws Exception;
    
}
