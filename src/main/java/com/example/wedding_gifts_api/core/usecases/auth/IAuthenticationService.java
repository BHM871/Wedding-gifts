package com.example.wedding_gifts_api.core.usecases.auth;

import java.util.UUID;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.example.wedding_gifts_api.core.domain.model.ChangeRequest;
import com.example.wedding_gifts_api.infra.dtos.authentication.ChangePassDTO;
import com.example.wedding_gifts_api.infra.dtos.authentication.ForgotPassDTO;

public interface IAuthenticationService extends UserDetailsService {

    public ChangeRequest forgotPassword(ForgotPassDTO forgotPass) throws Exception;
    
    public String changePassword(boolean isAccount, UUID request, ChangePassDTO change) throws Exception;

}
