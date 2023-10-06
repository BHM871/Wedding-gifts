package com.example.wedding_gifts.application.repositories;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import com.example.wedding_gifts.core.domain.dtos.account.AccountResponseAccountDTO;
import com.example.wedding_gifts.core.domain.dtos.account.CreateAccountDTO;
import com.example.wedding_gifts.core.domain.dtos.account.UpdateAccountDTO;
import com.example.wedding_gifts.core.domain.model.Account;
import com.example.wedding_gifts.core.usecases.account.IAccountRepository;
import com.example.wedding_gifts.infra.jpa.JpaAccountRespository;

@Repository
public class AccountRepository implements IAccountRepository {

    @Autowired
    JpaAccountRespository thisJpaRespository;

    @Override
    public Account save(Account account) throws Exception {
        return thisJpaRespository.save(account);
    }

    @Override
    public AccountResponseAccountDTO createAccount(CreateAccountDTO account) throws Exception {
        Account newAccount = new Account(account);

        return new AccountResponseAccountDTO(
            newAccount.getId(), 
            newAccount.getBrideGroom(), 
            newAccount.getWeddingDate(), 
            newAccount.getFirstName(), 
            newAccount.getLastName(), 
            newAccount.getEmail()); 
    }

    @Override
    public UserDetails getByEmail(String email){
        return thisJpaRespository.findByEmail(email);
    }

    @Override
    public UUID verificForGifter(String brindAndGifter) throws Exception {
        Account account = thisJpaRespository.findByBrideGroom(brindAndGifter).orElseThrow(() -> new Exception("Bride and groom not found"));
        
        return account.getId();
    }

    @Override
    public Account getAccountById(UUID id) throws Exception {
        return thisJpaRespository.findById(id).orElseThrow(() -> new Exception("Account not found"));
    }

    @Override
    public AccountResponseAccountDTO updateAccount(UpdateAccountDTO account, UUID id) throws Exception {
        Account upAccount = getAccountById(id);
        
        upAccount = upAccount.update(account);

        save(upAccount);

        return new AccountResponseAccountDTO(
            upAccount.getId(), 
            upAccount.getBrideGroom(), 
            upAccount.getWeddingDate(), 
            upAccount.getFirstName(), 
            upAccount.getLastName(), 
            upAccount.getEmail());
    }

    @Override
    public void deleteAccount(UUID id) throws Exception {
        Account account = getAccountById(id);
        thisJpaRespository.delete(account);
    }
    
}
