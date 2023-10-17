package com.example.wedding_gifts.application.repositories;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import com.example.wedding_gifts.core.domain.dtos.account.CreateAccountDTO;
import com.example.wedding_gifts.core.domain.dtos.account.UpdateAccountDTO;
import com.example.wedding_gifts.core.domain.exceptions.account.AccountNotFoundException;
import com.example.wedding_gifts.core.domain.model.Account;
import com.example.wedding_gifts.core.usecases.account.IAccountRepository;
import com.example.wedding_gifts.infra.jpa.JpaAccountRespository;

@Repository
public class AccountRepository implements IAccountRepository {

    @Autowired
    private JpaAccountRespository thisJpaRespository;

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
    public UserDetails getByEmail(String email) throws Exception {
        UserDetails user = thisJpaRespository.findByEmail(email);

        if(user == null) throw new AccountNotFoundException("Email not found");

        return user;
    }

    @Override
    public UUID verificForGifter(String brindAndGifter) throws Exception {
        Account account = thisJpaRespository.findByBrideGroom(brindAndGifter)
            .orElseThrow(() -> new AccountNotFoundException("Bride and groom not found"));
        
        return account.getId();
    }

    @Override
    public Account getAccountById(UUID id) throws Exception {
        return thisJpaRespository.findById(id)
            .orElseThrow(() -> new AccountNotFoundException("Account not found"));
    }

    @Override
    public Account updateAccount(UpdateAccountDTO account, UUID id) throws Exception {
        try{
            Account upAccount = getAccountById(id);
        
            upAccount = upAccount.update(account);

            return save(upAccount);
        } catch (AccountNotFoundException e){
            throw new AccountNotFoundException("ID shared not exists");
        } catch (Exception e){
            throw new Exception(e);
        }
    }

    @Override
    public void deleteAccount(UUID id) throws Exception {
        try{
            Account account = getAccountById(id);
            thisJpaRespository.delete(account);
        } catch (AccountNotFoundException e){
            throw new AccountNotFoundException("ID shared not exists");
        } catch (Exception e){
            throw new Exception(e);
        }
    }
    
}
