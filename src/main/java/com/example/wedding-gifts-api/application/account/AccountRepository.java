package com.example.wedding_gifts.application.account;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import com.example.wedding_gifts.core.domain.exceptions.account.AccountExecutionException;
import com.example.wedding_gifts.core.domain.exceptions.account.AccountNotFoundException;
import com.example.wedding_gifts.core.domain.exceptions.common.MyException;
import com.example.wedding_gifts.core.domain.model.Account;
import com.example.wedding_gifts.core.usecases.account.IAccountRepository;
import com.example.wedding_gifts.infra.dtos.account.CreateAccountDTO;
import com.example.wedding_gifts.infra.dtos.account.UpdateAccountDTO;
import com.example.wedding_gifts.infra.jpa.JpaAccountRespository;

@Repository
@SuppressWarnings("null")
public class AccountRepository implements IAccountRepository {

    @Autowired
    private JpaAccountRespository jpaRepository;

    @Override
    public Account save(Account account) throws Exception {
        try{
            return jpaRepository.save(account);
        } catch (Exception e) {
            throw new AccountExecutionException("Account can't be saved", e);
        }
    }

    @Override
    public Account createAccount(CreateAccountDTO account) throws Exception {
        try{    
            Account newAccount = new Account(account);

            return save(newAccount);
        } catch (MyException e) {
            throw e;
        } catch (Exception e) {
            throw new AccountExecutionException("Account can't be created", e);
        }
    }

    @Override
    public UserDetails getByEmail(String email) throws Exception {
        return jpaRepository.findByEmail(email);
    }

    @Override
    public UUID verificForGifter(String brindAndGifter) throws Exception {
        Account account = jpaRepository.findByBrideGroom(brindAndGifter)
            .orElseThrow(() -> new AccountNotFoundException("Bride and groom not exists"));
        
        return account.getId();
    }

    @Override
    public Account getAccountById(UUID id) throws Exception {
        return jpaRepository.findById(id)
            .orElseThrow(() -> new AccountNotFoundException("Account not exists"));
    }

    @Override
    public Account updateAccount(UUID account, UpdateAccountDTO update) throws Exception {
        try{
            Account upAccount = getAccountById(account);
        
            upAccount.update(update);

            return save(upAccount);
        } catch (AccountNotFoundException e){
            throw new AccountNotFoundException("ID shared not exists", e);
        } catch (MyException e){
            throw e;
        } catch (Exception e){
            throw new AccountExecutionException("Account can't be updated", e);
        }
    }

    @Override
    public void deleteAccount(UUID id) throws Exception {
        try{
            Account account = getAccountById(id);
            jpaRepository.delete(account);
        } catch (AccountNotFoundException e){
            throw new AccountNotFoundException("ID shared not exists", e);
        } catch (Exception e){
            throw new AccountExecutionException("Account can't be deleted", e);
        }
    }
    
}
