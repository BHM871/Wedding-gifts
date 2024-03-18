package com.example.wedding_gifts_api.infra.jpa;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.wedding_gifts_api.core.domain.model.ChangeRequest;
import java.util.List;


public interface JpaChangeRequestRepository extends JpaRepository<ChangeRequest, UUID> {

    @Query(nativeQuery = true, value = "SELECT * " +
                                        "FROM tb_change_request AS cre" +
                                            "JOIN tb_account AS acc" +
                                            "ON cre.account_id = acc.id " +
                                        "WHERE acc.email = :email")
    public List<ChangeRequest> findByEmail(String email);    

}
