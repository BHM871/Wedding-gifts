package com.example.wedding_gifts_api.core.usecases.exception;

import org.springframework.http.ResponseEntity;

import com.example.wedding_gifts_api.core.domain.exceptions.common.ExecutionException;
import com.example.wedding_gifts_api.core.domain.exceptions.common.ForbiddenException;
import com.example.wedding_gifts_api.core.domain.exceptions.common.InvalidValueException;
import com.example.wedding_gifts_api.core.domain.exceptions.common.NotFoundException;
import com.example.wedding_gifts_api.core.domain.exceptions.common.NotNullableException;
import com.example.wedding_gifts_api.core.domain.exceptions.common.NotYourException;
import com.example.wedding_gifts_api.infra.dtos.exception.ExceptionResponseDTO;

public interface IExceptionResponse {
    
    public ResponseEntity<ExceptionResponseDTO> execution(ExecutionException exception);
    
    public ResponseEntity<ExceptionResponseDTO> invalidValue(InvalidValueException exception);
    
    public ResponseEntity<ExceptionResponseDTO> notFound(NotFoundException exception);
    
    public ResponseEntity<ExceptionResponseDTO> notNullable(NotNullableException exception);
    
    public ResponseEntity<ExceptionResponseDTO> notYour(NotYourException exception);

    public ResponseEntity<ExceptionResponseDTO> forbidden(ForbiddenException exception);

}
