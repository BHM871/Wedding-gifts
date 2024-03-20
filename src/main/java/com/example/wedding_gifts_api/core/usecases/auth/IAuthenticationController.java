package com.example.wedding_gifts_api.core.usecases.auth;

import java.util.UUID;

import org.springframework.http.ResponseEntity;

import com.example.wedding_gifts_api.infra.dtos.account.AccountResponseAccountDTO;
import com.example.wedding_gifts_api.infra.dtos.account.CreateAccountDTO;
import com.example.wedding_gifts_api.infra.dtos.account.LoginDTO;
import com.example.wedding_gifts_api.infra.dtos.authentication.AuthenticationResponseDTO;
import com.example.wedding_gifts_api.infra.dtos.authentication.ChangePassLoggedDTO;
import com.example.wedding_gifts_api.infra.dtos.authentication.ChangePassNotLoggedDTO;
import com.example.wedding_gifts_api.infra.dtos.authentication.ForgotPassDTO;
import com.example.wedding_gifts_api.infra.dtos.change_request.ChangeRequestDTO;
import com.example.wedding_gifts_api.infra.dtos.commun.MessageDTO;

public interface IAuthenticationController {

    public ResponseEntity<AuthenticationResponseDTO> login(LoginDTO login) throws Exception;

    public ResponseEntity<MessageDTO> logout(String token) throws Exception;

    public ResponseEntity<AccountResponseAccountDTO> register(CreateAccountDTO account) throws Exception;

    public ResponseEntity<ChangeRequestDTO> forgotPassword(ForgotPassDTO forgetRequest) throws Exception;

    public ResponseEntity<MessageDTO> changePassword(UUID request, ChangePassNotLoggedDTO change) throws Exception;

    public ResponseEntity<MessageDTO> changePassword(String token, UUID account, ChangePassLoggedDTO change) throws Exception;
    
}
