package com.example.wedding_gifts.infra.exception;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.example.wedding_gifts.common.MyZoneOffSet;
import com.example.wedding_gifts.core.domain.dtos.exception.ExceptionResponseDTO;
import com.example.wedding_gifts.core.domain.exceptions.account.AccountExecutionException;
import com.example.wedding_gifts.core.domain.exceptions.account.AccountInvalidValueException;
import com.example.wedding_gifts.core.domain.exceptions.account.AccountNotFoundException;
import com.example.wedding_gifts.core.domain.exceptions.account.AccountNotNullableException;
import com.example.wedding_gifts.core.domain.exceptions.common.ExecutionException;
import com.example.wedding_gifts.core.domain.exceptions.common.InvalidValueException;
import com.example.wedding_gifts.core.domain.exceptions.common.NotFoundException;
import com.example.wedding_gifts.core.domain.exceptions.common.NotNullableException;
import com.example.wedding_gifts.core.domain.exceptions.common.NotYourException;
import com.example.wedding_gifts.core.usecases.exception.IExceptionResponse;

@RestControllerAdvice
public class AccountExceptionHandler implements IExceptionResponse {

    @Override
    @ExceptionHandler(AccountExecutionException.class)
    public ResponseEntity<ExceptionResponseDTO> execution(ExecutionException exception) {
        return exception.getResponse();
    }

    @Override
    @ExceptionHandler(AccountInvalidValueException.class)
    public ResponseEntity<ExceptionResponseDTO> invalidValue(InvalidValueException exception) {
        return exception.getResponse();
    }

    @Override
    @ExceptionHandler(AccountNotFoundException.class)
    public ResponseEntity<ExceptionResponseDTO> notFound(NotFoundException exception) {
        return exception.getResponse();
    }

    @Override
    @ExceptionHandler(AccountNotNullableException.class)
    public ResponseEntity<ExceptionResponseDTO> notNullable(NotNullableException exception) {
        return exception.getResponse();
    }

    @Override
    public ResponseEntity<ExceptionResponseDTO> notYour(NotYourException exception) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ExceptionResponseDTO(
            LocalDateTime.now(MyZoneOffSet.zoneId()), 
            400, 
            "Bad Request", 
            "MyException.class", 
            "Exception", 
            "Undefined"));
    }
    
}
