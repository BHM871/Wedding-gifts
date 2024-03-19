package com.example.wedding_gifts_api.infra.dtos.change_request;

import java.time.LocalDateTime;
import java.util.UUID;

import com.example.wedding_gifts_api.core.domain.model.util.ChangeRequestType;
import com.example.wedding_gifts_api.infra.dtos.account.AccountResponseIdDTO;

public record ChangeRequestDTO(
    UUID id,
    ChangeRequestType requestType,
    LocalDateTime request,
    LocalDateTime limitHour,
    AccountResponseIdDTO account
){}
