package com.example.wedding_gifts_api.core.usecases.auth;

import java.util.UUID;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.example.wedding_gifts_api.infra.dtos.authentication.ChangePassLoggedDTO;
import com.example.wedding_gifts_api.infra.dtos.authentication.ChangePassNotLoggedDTO;
import com.example.wedding_gifts_api.infra.dtos.authentication.ForgotPassDTO;
import com.example.wedding_gifts_api.infra.dtos.change_request.ChangeRequestDTO;

public interface IAuthenticationService extends UserDetailsService {

    public ChangeRequestDTO forgotPassword(ForgotPassDTO forgotPass) throws Exception;
    
    public String changePassword(UUID request, ChangePassNotLoggedDTO change) throws Exception;

    public String changePassword(UUID account, ChangePassLoggedDTO change) throws Exception;

}
