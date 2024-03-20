package com.example.wedding_gifts_api.infra.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.example.wedding_gifts_api.core.domain.exceptions.change_request.ChangeRequestExecutionException;
import com.example.wedding_gifts_api.core.domain.exceptions.change_request.ChangeRequestInvalidValueException;
import com.example.wedding_gifts_api.core.domain.exceptions.change_request.ChangeRequestNotFoundException;
import com.example.wedding_gifts_api.core.domain.exceptions.change_request.ChangeRequestNotNullableException;
import com.example.wedding_gifts_api.core.domain.exceptions.common.ExecutionException;
import com.example.wedding_gifts_api.core.domain.exceptions.common.ForbiddenException;
import com.example.wedding_gifts_api.core.domain.exceptions.common.InvalidValueException;
import com.example.wedding_gifts_api.core.domain.exceptions.common.NotFoundException;
import com.example.wedding_gifts_api.core.domain.exceptions.common.NotNullableException;
import com.example.wedding_gifts_api.core.domain.exceptions.common.NotYourException;
import com.example.wedding_gifts_api.core.usecases.exception.IExceptionResponse;
import com.example.wedding_gifts_api.infra.dtos.exception.ExceptionResponseDTO;

@RestControllerAdvice
public class ChangeRequestExceptionHandler implements IExceptionResponse {

    @Override
    @ExceptionHandler(ChangeRequestExecutionException.class)
    public ResponseEntity<ExceptionResponseDTO> execution(ExecutionException exception) {
        return exception.getResponse();
    }

    @Override
    @ExceptionHandler(ChangeRequestInvalidValueException.class)
    public ResponseEntity<ExceptionResponseDTO> invalidValue(InvalidValueException exception) {
        return exception.getResponse();
    }

    @Override
    @ExceptionHandler(ChangeRequestNotFoundException.class)
    public ResponseEntity<ExceptionResponseDTO> notFound(NotFoundException exception) {
        return exception.getResponse();
    }

    @Override
    @ExceptionHandler(ChangeRequestNotNullableException.class)
    public ResponseEntity<ExceptionResponseDTO> notNullable(NotNullableException exception) {
        return exception.getResponse();
    }

    @Override
    public ResponseEntity<ExceptionResponseDTO> notYour(NotYourException exception) {
        throw new UnsupportedOperationException("Unimplemented method 'notYour'");
    }

    @Override
    public ResponseEntity<ExceptionResponseDTO> forbidden(ForbiddenException exception) {
        throw new UnsupportedOperationException("Unimplemented method 'notYour'");
    }
    
}