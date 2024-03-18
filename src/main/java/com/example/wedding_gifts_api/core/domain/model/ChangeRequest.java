package com.example.wedding_gifts_api.core.domain.model;

import java.time.LocalDateTime;
import java.util.UUID;

import com.example.wedding_gifts_api.common.MyZone;
import com.example.wedding_gifts_api.core.domain.model.util.ChangeRequestType;
import com.example.wedding_gifts_api.infra.dtos.authentication.ForgotPassDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ChangeRequest {

    private UUID id;

    private ChangeRequestType type; 
    
    private LocalDateTime request;

    private LocalDateTime limit;

    private Account account;
    
    public ChangeRequest(ForgotPassDTO forgotPassDTO, Account account){
        this.type = ChangeRequestType.PASSWORD;
        this.account = account;
        this.request = LocalDateTime.now(MyZone.zoneId());
        this.limit = LocalDateTime.now(MyZone.zoneId()).plusMinutes(5);
    }
}