package com.example.wedding_gifts_api.infra.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.example.wedding_gifts_api.core.domain.exceptions.common.ExecutionException;
import com.example.wedding_gifts_api.core.domain.exceptions.common.ForbiddenException;
import com.example.wedding_gifts_api.core.domain.exceptions.common.InvalidValueException;
import com.example.wedding_gifts_api.core.domain.exceptions.common.NotFoundException;
import com.example.wedding_gifts_api.core.domain.exceptions.common.NotNullableException;
import com.example.wedding_gifts_api.core.domain.exceptions.common.NotYourException;
import com.example.wedding_gifts_api.core.domain.exceptions.gift.GiftExecutionException;
import com.example.wedding_gifts_api.core.domain.exceptions.gift.GiftInvalidValueException;
import com.example.wedding_gifts_api.core.domain.exceptions.gift.GiftNotFoundException;
import com.example.wedding_gifts_api.core.domain.exceptions.gift.GiftNotNullableException;
import com.example.wedding_gifts_api.core.domain.exceptions.gift.GiftNotYourException;
import com.example.wedding_gifts_api.core.usecases.exception.IExceptionResponse;
import com.example.wedding_gifts_api.infra.dtos.exception.ExceptionResponseDTO;

@RestControllerAdvice
public class GiftExceptionHandler implements IExceptionResponse {

    @Override
    @ExceptionHandler(GiftExecutionException.class)
    public ResponseEntity<ExceptionResponseDTO> execution(ExecutionException exception) {
        return exception.getResponse();
    }

    @Override
    @ExceptionHandler(GiftInvalidValueException.class)
    public ResponseEntity<ExceptionResponseDTO> invalidValue(InvalidValueException exception) {
        return exception.getResponse();
    }

    @Override
    @ExceptionHandler(GiftNotFoundException.class)
    public ResponseEntity<ExceptionResponseDTO> notFound(NotFoundException exception) {
        return exception.getResponse();
    }

    @Override
    @ExceptionHandler(GiftNotNullableException.class)
    public ResponseEntity<ExceptionResponseDTO> notNullable(NotNullableException exception) {
        return exception.getResponse();
    }

    @Override
    @ExceptionHandler(GiftNotYourException.class)
    public ResponseEntity<ExceptionResponseDTO> notYour(NotYourException exception) {
        return exception.getResponse();
    }

    @Override
    public ResponseEntity<ExceptionResponseDTO> forbidden(ForbiddenException exception) {
        throw new UnsupportedOperationException("Unimplemented method 'forbidden'");
    }
    
}