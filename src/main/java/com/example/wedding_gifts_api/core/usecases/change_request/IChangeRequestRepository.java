package com.example.wedding_gifts_api.core.usecases.change_request;

import java.util.UUID;

import com.example.wedding_gifts_api.core.domain.model.ChangeRequest;

public interface IChangeRequestRepository {
    
    public ChangeRequest saveRequest(ChangeRequest request) throws Exception;

    public ChangeRequest getRequestById(UUID request) throws Exception;

    public ChangeRequest getRequestsByEmail(String email) throws Exception;

}
