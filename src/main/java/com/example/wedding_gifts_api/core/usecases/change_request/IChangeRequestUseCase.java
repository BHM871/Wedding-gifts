package com.example.wedding_gifts_api.core.usecases.change_request;

import java.util.List;
import java.util.UUID;

import com.example.wedding_gifts_api.core.domain.model.ChangeRequest;
import com.example.wedding_gifts_api.infra.dtos.authentication.ForgotPassDTO;

public interface IChangeRequestUseCase {

    public ChangeRequest forgotPassword(ForgotPassDTO forgotRequest) throws Exception;

    public ChangeRequest getRequestById(UUID request) throws Exception;

    public List<ChangeRequest> getRequestByEmail(String email) throws Exception;

    public void deleteRequestById(UUID request) throws Exception;

}
