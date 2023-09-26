package com.example.wedding_gifts.core.usecases.account;

import java.util.UUID;

import org.springframework.http.ResponseEntity;

import com.example.wedding_gifts.core.domain.dtos.account.UpdateAccountDTO;
import com.example.wedding_gifts.core.domain.model.Account;

public interface AccountController {

    public ResponseEntity<UUID> gifterBegin(String brideGroom) throws Exception;

    public ResponseEntity<Account> getAccountById(UUID id) throws Exception;

    public ResponseEntity<Account> updateAccount(UpdateAccountDTO account, UUID id) throws Exception;

    public ResponseEntity<String> deleteAccount(UUID id) throws Exception;
}
