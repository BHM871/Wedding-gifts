package com.example.wedding_gifts_api.core.domain.model;

import java.time.LocalDateTime;
import java.util.UUID;

import com.example.wedding_gifts_api.common.MyZone;
import com.example.wedding_gifts_api.core.domain.model.util.ChangeRequestType;
import com.example.wedding_gifts_api.infra.dtos.authentication.ForgotPassDTO;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "tb_change_request")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ChangeRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Enumerated(EnumType.STRING)
    private ChangeRequestType requestType; 
    
    private LocalDateTime request;

    private LocalDateTime limitHour;

    @ManyToOne
    @JoinColumn(name = "account_id")
    private Account account;
    
    public ChangeRequest(ForgotPassDTO forgotPassDTO, Account account){
        this.requestType = ChangeRequestType.PASSWORD;
        this.account = account;
        this.request = LocalDateTime.now(MyZone.zoneId());
        this.limitHour = LocalDateTime.now(MyZone.zoneId()).plusMinutes(5);
    }
}