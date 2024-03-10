package com.example.wedding_gifts_api.core.usecases.account;

import java.util.UUID;

import org.springframework.http.ResponseEntity;

import com.example.wedding_gifts_api.infra.dtos.account.AccountResponseAccountDTO;
import com.example.wedding_gifts_api.infra.dtos.account.AccountResponseIdDTO;
import com.example.wedding_gifts_api.infra.dtos.account.UpdateAccountDTO;
import com.example.wedding_gifts_api.infra.dtos.commun.MessageDTO;

public interface IAccountController {

    public ResponseEntity<AccountResponseIdDTO> gifterBegin(String brideGroom) throws Exception;

    public ResponseEntity<AccountResponseAccountDTO> getAccountById(UUID account) throws Exception;

    public ResponseEntity<AccountResponseAccountDTO> updateAccount(String token, UUID account, UpdateAccountDTO update) throws Exception;

    public ResponseEntity<MessageDTO> deleteAccount(String token, UUID account) throws Exception;
}
