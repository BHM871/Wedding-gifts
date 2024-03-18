package com.example.wedding_gifts_api.application.change_request;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

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
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public ChangeRequest getRequestById(UUID request) throws Exception {
        return jpaRepository.findById(request).orElseThrow(() -> new Exception());
    }

    @Override
    public List<ChangeRequest> getRequestsByEmail(String email) throws Exception {
        return jpaRepository.findByEmail(email);
    }

    @Override
    public void deleteById(UUID request) throws Exception {
        try {
            jpaRepository.deleteById(request);
        } catch (Exception e) {
            throw e;
        }
    }
    
}
