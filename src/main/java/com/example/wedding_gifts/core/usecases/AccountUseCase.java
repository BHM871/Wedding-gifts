package com.example.wedding_gifts.core.usecases;

import com.example.wedding_gifts.core.domain.dtos.account.CreateAccountDTO;
import com.example.wedding_gifts.core.domain.dtos.account.LoginDTO;
import com.example.wedding_gifts.core.domain.dtos.account.UpdateAccountDTO;
import com.example.wedding_gifts.core.domain.model.Account;

public interface AccountUseCase {

    public Account createAccount(CreateAccountDTO account) throws Exception;

    public String login(LoginDTO login) throws Exception;

    public void verificAccountForGifter(String brideAndGroom) throws Exception;

    public Account getAccountById(String id) throws Exception;

    public Account updateAccount(UpdateAccountDTO account) throws Exception;

    public void deleteAccount(String id) throws Exception;
    
}
