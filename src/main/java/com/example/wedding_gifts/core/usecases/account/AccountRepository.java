package com.example.wedding_gifts.core.usecases.account;

import java.util.UUID;

import org.springframework.security.core.userdetails.UserDetails;

import com.example.wedding_gifts.core.domain.dtos.account.CreateAccountDTO;
import com.example.wedding_gifts.core.domain.dtos.account.UpdateAccountDTO;
import com.example.wedding_gifts.core.domain.model.Account;

public interface AccountRepository {

    public Account save(Account account) throws Exception;

    public Account createAccount(CreateAccountDTO accountDTO) throws Exception;

    public UserDetails getByEmail(String email);

    public UUID verificForGifter(String brindAndGifter) throws Exception;

    public Account getAccountById(UUID id) throws Exception;

    public Account updateAccount(UpdateAccountDTO account, UUID id) throws Exception;

    public void deleteAccount(UUID id) throws Exception;
    
}
