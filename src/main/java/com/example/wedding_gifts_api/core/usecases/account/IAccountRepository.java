package com.example.wedding_gifts_api.core.usecases.account;

import java.util.UUID;

import org.springframework.security.core.userdetails.UserDetails;

import com.example.wedding_gifts_api.core.domain.model.Account;
import com.example.wedding_gifts_api.infra.dtos.account.CreateAccountDTO;
import com.example.wedding_gifts_api.infra.dtos.account.UpdateAccountDTO;

public interface IAccountRepository {

    public Account save(Account account) throws Exception;

    public Account createAccount(CreateAccountDTO accountDTO) throws Exception;

    public UserDetails getByEmail(String email) throws Exception;

    public Account getAccountByEmail(String email) throws Exception;

    public Account getAccountById(UUID id) throws Exception;

    public UUID verificForGifter(String brindAndGifter) throws Exception;

    public Account updateAccount(UUID account, UpdateAccountDTO update) throws Exception;

    public void deleteAccount(UUID account) throws Exception;
    
}
