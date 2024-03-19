package com.example.wedding_gifts_api.application.change_request;

import java.util.ArrayList;
import java.util.List;
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
    public List<ChangeRequestDTO> getRequestByEmail(String email) throws Exception {
        List<ChangeRequest> requests = repository.getRequestsByEmail(email);
        List<ChangeRequestDTO> response = new ArrayList<ChangeRequestDTO>();

        for (ChangeRequest request : requests) {
            response.add(toResponse(request));
        }

        return response;
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
