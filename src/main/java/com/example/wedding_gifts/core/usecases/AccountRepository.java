package com.example.wedding_gifts.core.usecases;

import com.example.wedding_gifts.core.domain.dtos.account.CreateAccountDTO;
import com.example.wedding_gifts.core.domain.dtos.account.UpdateAccountDTO;
import com.example.wedding_gifts.core.domain.model.Account;

public interface AccountRepository {

    public Account save(Account account) throws Exception;

    public Account createAccount(CreateAccountDTO accountDTO) throws Exception;

    public String verificForGifter(String brindAndGifter) throws Exception;

    public Account getAccountById(String id) throws Exception;

    public Account updateAccount(UpdateAccountDTO account) throws Exception;

    public void deleteAccount(String id) throws Exception;
    
}
