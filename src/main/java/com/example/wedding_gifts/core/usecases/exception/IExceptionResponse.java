package com.example.wedding_gifts.core.usecases.exception;

import org.springframework.http.ResponseEntity;

import com.example.wedding_gifts.core.domain.dtos.exception.ExceptionResponseDTO;
import com.example.wedding_gifts.core.domain.exceptions.common.ExecutionException;
import com.example.wedding_gifts.core.domain.exceptions.common.InvalidValueException;
import com.example.wedding_gifts.core.domain.exceptions.common.NotFoundException;
import com.example.wedding_gifts.core.domain.exceptions.common.NotNullableException;
import com.example.wedding_gifts.core.domain.exceptions.common.NotYourException;

public interface IExceptionResponse {
    
    public ResponseEntity<ExceptionResponseDTO> execution(ExecutionException exception);
    
    public ResponseEntity<ExceptionResponseDTO> invalidValue(InvalidValueException exception);
    
    public ResponseEntity<ExceptionResponseDTO> notFound(NotFoundException exception);
    
    public ResponseEntity<ExceptionResponseDTO> notNullable(NotNullableException exception);
    
    public ResponseEntity<ExceptionResponseDTO> notYour(NotYourException exception);

}
