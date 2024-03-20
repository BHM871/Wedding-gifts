package com.example.wedding_gifts_api.application.change_request;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.stereotype.Repository;

import com.example.wedding_gifts_api.core.domain.exceptions.change_request.ChangeRequestExecutionException;
import com.example.wedding_gifts_api.core.domain.exceptions.change_request.ChangeRequestNotFoundException;
import com.example.wedding_gifts_api.core.domain.model.ChangeRequest;
import com.example.wedding_gifts_api.core.usecases.change_request.IChangeRequestRepository;
import com.example.wedding_gifts_api.infra.jpa.JpaChangeRequestRepository;

@Repository
@SuppressWarnings("null")
public class ChangeRequestRepository implements IChangeRequestRepository {

    @Autowired
    private JpaChangeRequestRepository jpaRepository;

    @Override
    public ChangeRequest saveRequest(ChangeRequest request) throws Exception {
        try {
            return jpaRepository.save(request);        
        } catch (OptimisticLockingFailureException e) {
            throw new ChangeRequestNotFoundException("Account already have a request, finalize that before", e);
        } catch (Exception e) {
            throw new ChangeRequestExecutionException("Some error", e);
        }
    }

    @Override
    public ChangeRequest getRequestById(UUID request) throws Exception {
        return jpaRepository.findById(request).orElseThrow(() -> new Exception("Request not found"));
    }

    @Override
    public ChangeRequest getRequestsByEmail(String email) throws Exception {
        return jpaRepository.findByEmail(email).orElseThrow(() -> new ChangeRequestNotFoundException("Any request not found"));
    }

    @Override
    public void deleteById(UUID request) throws Exception {
        try {
            jpaRepository.deleteById(request);
        } catch (IllegalArgumentException e) {
            throw new ChangeRequestNotFoundException("Request not found", e);
        } catch (Exception e) {
            throw new ChangeRequestExecutionException("Some error", e);
        }
    }
    
}
