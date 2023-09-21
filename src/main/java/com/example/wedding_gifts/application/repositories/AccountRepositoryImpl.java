package com.example.wedding_gifts.application.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.example.wedding_gifts.core.domain.dtos.account.CreateAccountDTO;
import com.example.wedding_gifts.core.domain.dtos.account.UpdateAccountDTO;
import com.example.wedding_gifts.core.domain.model.Account;
import com.example.wedding_gifts.core.usecases.AccountRepository;
import com.example.wedding_gifts.infra.jpa.JpaAccountRespository;

@Repository
public class AccountRepositoryImpl implements AccountRepository {

    @Autowired
    JpaAccountRespository thisJpaRespository;

    @Override
    public Account save(Account account) throws Exception {
        return thisJpaRespository.save(account);
    }

    @Override
    public Account createAccount(CreateAccountDTO account) throws Exception {
        Account newAccount = new Account(account);

        return save(newAccount);
    }

    @Override
    public String verificForGifter(String brindAndGifter) throws Exception {
        Account account = thisJpaRespository.findByBrideAndGroom(brindAndGifter).orElseThrow(() -> new Exception());
        
        return account.getId();
    }

    @Override
    public Account getAccountById(String id) throws Exception {
        return thisJpaRespository.findById(id).orElseThrow(() -> new Exception());
    }

    @Override
    public Account updateAccount(UpdateAccountDTO account, String id) throws Exception {
        Account upAccount = getAccountById(id);
        
        upAccount = upAccount.update(account);

        return save(upAccount);
    }

    @Override
    public void deleteAccount(String id) throws Exception {
        thisJpaRespository.deleteById(id);
    }
    
}
