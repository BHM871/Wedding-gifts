package com.example.wedding_gifts_api.core.usecases.auth;

import java.util.UUID;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.example.wedding_gifts_api.infra.dtos.authentication.ChangePassDTO;
import com.example.wedding_gifts_api.infra.dtos.authentication.ForgotPassDTO;
import com.example.wedding_gifts_api.infra.dtos.change_request.ChangeRequestDTO;

public interface IAuthenticationService extends UserDetailsService {

    public ChangeRequestDTO forgotPassword(ForgotPassDTO forgotPass) throws Exception;
    
    public String changePassword(boolean isAccount, UUID request, ChangePassDTO change) throws Exception;

}
