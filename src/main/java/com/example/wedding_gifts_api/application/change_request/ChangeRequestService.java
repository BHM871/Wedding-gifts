package com.example.wedding_gifts_api.application.change_request;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.wedding_gifts_api.core.domain.model.ChangeRequest;
import com.example.wedding_gifts_api.core.usecases.account.IAccountRepository;
import com.example.wedding_gifts_api.core.usecases.change_request.IChangeRequestRepository;
import com.example.wedding_gifts_api.core.usecases.change_request.IChangeRequestUseCase;
import com.example.wedding_gifts_api.infra.dtos.authentication.ForgotPassDTO;

public class ChangeRequestService implements IChangeRequestUseCase {

    @Autowired
    private IChangeRequestRepository repository;
    @Autowired
    private IAccountRepository accountRepository;

    @Override
    public ChangeRequest forgotPassword(ForgotPassDTO forgotRequest) throws Exception {
        ChangeRequest request = new ChangeRequest(
            forgotRequest, 
            accountRepository.getAccountByEmail(
                forgotRequest.email()
            )
        );

        return repository.saveRequest(request);
    }

    @Override
    public ChangeRequest getRequestById(UUID request) throws Exception {
        return repository.getRequestById(request);
    }

    @Override
    public List<ChangeRequest> getRequestByEmail(String email) throws Exception {
        return repository.getRequestsByEmail(email);
    }
    
}
