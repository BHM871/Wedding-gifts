package com.example.wedding_gifts_api.application.change_request;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.wedding_gifts_api.core.domain.model.ChangeRequest;
import com.example.wedding_gifts_api.core.usecases.account.IAccountRepository;
import com.example.wedding_gifts_api.core.usecases.change_request.IChangeRequestRepository;
import com.example.wedding_gifts_api.core.usecases.change_request.IChangeRequestUseCase;
import com.example.wedding_gifts_api.infra.dtos.account.AccountResponseIdDTO;
import com.example.wedding_gifts_api.infra.dtos.authentication.ForgotPassDTO;
import com.example.wedding_gifts_api.infra.dtos.change_request.ChangeRequestDTO;

@Service
public class ChangeRequestService implements IChangeRequestUseCase {

    @Autowired
    private IChangeRequestRepository repository;
    @Autowired
    private IAccountRepository accountRepository;

    @Override
    public ChangeRequestDTO forgotPassword(ForgotPassDTO forgotRequest) throws Exception {
        ChangeRequest request = new ChangeRequest(
            forgotRequest, 
            accountRepository.getAccountByEmail(
                forgotRequest.email()
            )
        );

        request = repository.saveRequest(request);

        return toResponse(request);
    }

    @Override
    public ChangeRequest getRequestById(UUID request) throws Exception {
        return repository.getRequestById(request);
        }

    @Override
    public ChangeRequestDTO getRequestByEmail(String email) throws Exception {
        ChangeRequest requests = repository.getRequestsByEmail(email);
        return toResponse(requests);
    }

    @Override
    public void deleteRequestById(UUID request) throws Exception {
        repository.deleteById(request);
    }
    
    private ChangeRequestDTO toResponse(ChangeRequest request){
        return new ChangeRequestDTO(
            request.getId(), 
            request.getRequestType(), 
            request.getRequest(), 
            request.getLimitHour(), 
            new AccountResponseIdDTO(
                request.getAccount().getId()
            )
        );
    }
    
}
