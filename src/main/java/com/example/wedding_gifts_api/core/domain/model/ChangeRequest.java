package com.example.wedding_gifts_api.core.domain.model;

import java.time.LocalDateTime;
import java.util.UUID;

import com.example.wedding_gifts_api.core.domain.model.util.ChangeRequestType;

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

    private String email;

    private String brideGroom;
    
    private LocalDateTime request;

    private LocalDateTime limit;
    
}
