package com.example.wedding_gifts.infra.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.example.wedding_gifts.core.domain.dtos.exception.ExceptionResponseDTO;
import com.example.wedding_gifts.core.domain.exceptions.common.MyException;

@RestControllerAdvice
public class AccountExceptionHandler {

    @ExceptionHandler(MyException.class)
    public ResponseEntity<ExceptionResponseDTO> myException(MyException exception) {
        return exception.getResponse();
    }
    
}
