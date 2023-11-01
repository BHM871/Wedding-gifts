package com.example.wedding_gifts.infra.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.example.wedding_gifts.core.domain.dtos.exception.ExceptionResponseDTO;
import com.example.wedding_gifts.core.domain.exceptions.common.ExecutionException;
import com.example.wedding_gifts.core.domain.exceptions.common.InvalidValueException;
import com.example.wedding_gifts.core.domain.exceptions.common.NotFoundException;
import com.example.wedding_gifts.core.domain.exceptions.common.NotNullableException;
import com.example.wedding_gifts.core.domain.exceptions.common.NotYourException;
import com.example.wedding_gifts.core.domain.exceptions.token.TokenExecutionException;
import com.example.wedding_gifts.core.domain.exceptions.token.TokenInvalidValueException;
import com.example.wedding_gifts.core.usecases.exception.IExceptionResponse;

@RestControllerAdvice
public class TokenExceptionHandler implements IExceptionResponse {

    @Override
    @ExceptionHandler(TokenExecutionException.class)
    public ResponseEntity<ExceptionResponseDTO> execution(ExecutionException exception) {
        return exception.getResponse();
    }

    @Override
    @ExceptionHandler(TokenInvalidValueException.class)
    public ResponseEntity<ExceptionResponseDTO> invalidValue(InvalidValueException exception) {
        return exception.getResponse();
    }

    @Override
    public ResponseEntity<ExceptionResponseDTO> notFound(NotFoundException exception) {
        throw new UnsupportedOperationException("Unimplemented method 'notFound'");
    }

    @Override
    public ResponseEntity<ExceptionResponseDTO> notNullable(NotNullableException exception) {
        throw new UnsupportedOperationException("Unimplemented method 'notNullable'");
    }

    @Override
    public ResponseEntity<ExceptionResponseDTO> notYour(NotYourException exception) {
        throw new UnsupportedOperationException("Unimplemented method 'notYour'");
    }

    @Override
    public ResponseEntity<ExceptionResponseDTO> forbidden(Exception exception) {
        throw new UnsupportedOperationException("Unimplemented method 'forbidden'");
    }
    
}