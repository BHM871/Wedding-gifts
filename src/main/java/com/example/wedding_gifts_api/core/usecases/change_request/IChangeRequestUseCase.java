package com.example.wedding_gifts_api.core.usecases.change_request;

import java.util.UUID;

import com.example.wedding_gifts_api.core.domain.model.ChangeRequest;
import com.example.wedding_gifts_api.infra.dtos.authentication.ForgotPassDTO;
import com.example.wedding_gifts_api.infra.dtos.change_request.ChangeRequestDTO;

public interface IChangeRequestUseCase {

    public ChangeRequestDTO forgotPassword(ForgotPassDTO forgotRequest) throws Exception;

    public ChangeRequest getRequestById(UUID request) throws Exception;

    public ChangeRequestDTO getRequestByEmail(String email) throws Exception;

    public void deleteRequestById(UUID request) throws Exception;

}
