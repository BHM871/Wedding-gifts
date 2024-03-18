package com.example.wedding_gifts_api.core.usecases.auth;

import java.util.UUID;

import org.springframework.http.ResponseEntity;

import com.example.wedding_gifts_api.core.domain.model.ChangeRequest;
import com.example.wedding_gifts_api.infra.dtos.account.AccountResponseAccountDTO;
import com.example.wedding_gifts_api.infra.dtos.account.CreateAccountDTO;
import com.example.wedding_gifts_api.infra.dtos.account.LoginDTO;
import com.example.wedding_gifts_api.infra.dtos.authentication.AuthenticationResponseDTO;
import com.example.wedding_gifts_api.infra.dtos.authentication.ChangePassDTO;
import com.example.wedding_gifts_api.infra.dtos.authentication.ForgotPassDTO;
import com.example.wedding_gifts_api.infra.dtos.commun.MessageDTO;

public interface IAuthenticationController {

    public ResponseEntity<AuthenticationResponseDTO> login(LoginDTO login) throws Exception;

    public ResponseEntity<MessageDTO> logout(String token) throws Exception;

    public ResponseEntity<AccountResponseAccountDTO> register(CreateAccountDTO account) throws Exception;

    public ResponseEntity<ChangeRequest> forgotPassword(ForgotPassDTO forgetRequest) throws Exception;

    public ResponseEntity<MessageDTO> changePassword(UUID request, ChangePassDTO change) throws Exception;

    public ResponseEntity<MessageDTO> changePassword(String token, UUID account, ChangePassDTO change) throws Exception;
    
}
