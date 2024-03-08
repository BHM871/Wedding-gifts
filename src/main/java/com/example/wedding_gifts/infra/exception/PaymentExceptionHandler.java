package com.example.wedding_gifts.infra.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.example.wedding_gifts.core.domain.exceptions.common.ExecutionException;
import com.example.wedding_gifts.core.domain.exceptions.common.GatewayException;
import com.example.wedding_gifts.core.domain.exceptions.common.NotFoundException;
import com.example.wedding_gifts.core.domain.exceptions.payment.PaymentExecutionException;
import com.example.wedding_gifts.core.domain.exceptions.payment.PaymentGatewayException;
import com.example.wedding_gifts.core.domain.exceptions.payment.PaymentNotFoundException;
import com.example.wedding_gifts.core.usecases.exception.IGatewayExceptionResponse;
import com.example.wedding_gifts.infra.dtos.exception.ExceptionResponseDTO;

@RestControllerAdvice
public class PaymentExceptionHandler extends IGatewayExceptionResponse {

    @Override
    @ExceptionHandler(PaymentExecutionException.class)
    public ResponseEntity<ExceptionResponseDTO> execution(ExecutionException exception) throws Exception {
        return exception.getResponse();
    }

    @Override
    @ExceptionHandler(PaymentGatewayException.class)
    public ResponseEntity<ExceptionResponseDTO> badGateway(GatewayException exception) throws Exception {
        return exception.getResponse(); 
    }

    @Override
    @ExceptionHandler(PaymentNotFoundException.class)
    public ResponseEntity<ExceptionResponseDTO> notFound(NotFoundException exception) throws Exception {
        return exception.getResponse();
    }
    
}
