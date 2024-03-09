package com.example.wedding_gifts.infra.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.example.wedding_gifts.core.domain.exceptions.common.ExecutionException;
import com.example.wedding_gifts.core.domain.exceptions.common.ForbiddenException;
import com.example.wedding_gifts.core.domain.exceptions.common.GatewayException;
import com.example.wedding_gifts.core.domain.exceptions.common.InvalidValueException;
import com.example.wedding_gifts.core.domain.exceptions.common.NotFoundException;
import com.example.wedding_gifts.core.domain.exceptions.common.NotNullableException;
import com.example.wedding_gifts.core.domain.exceptions.common.NotYourException;
import com.example.wedding_gifts.core.domain.exceptions.payment.PaymentCertificateException;
import com.example.wedding_gifts.core.domain.exceptions.payment.PaymentExecutionException;
import com.example.wedding_gifts.core.domain.exceptions.payment.PaymentGatewayException;
import com.example.wedding_gifts.core.domain.exceptions.payment.PaymentHttpException;
import com.example.wedding_gifts.core.domain.exceptions.payment.PaymentNotFoundException;
import com.example.wedding_gifts.core.usecases.exception.IExceptionResponse;
import com.example.wedding_gifts.core.usecases.exception.IGatewayExceptionResponse;
import com.example.wedding_gifts.infra.dtos.exception.ExceptionResponseDTO;

@RestControllerAdvice
public class PaymentExceptionHandler extends IGatewayExceptionResponse implements IExceptionResponse {

    @Override
    @ExceptionHandler(PaymentGatewayException.class)
    public ResponseEntity<ExceptionResponseDTO> badGateway(GatewayException exception) throws Exception {
        return exception.getResponse(); 
    }

    @Override
    @ExceptionHandler(PaymentCertificateException.class)
    public ResponseEntity<ExceptionResponseDTO> certificate(ExecutionException exception) throws Exception {
        return exception.getResponse();
    }

    @Override
    @ExceptionHandler(PaymentHttpException.class)
    public ResponseEntity<ExceptionResponseDTO> http(ExecutionException exception) throws Exception {
        return exception.getResponse();
    }

    @Override
    @ExceptionHandler(PaymentExecutionException.class)
    public ResponseEntity<ExceptionResponseDTO> execution(ExecutionException exception) {
        return exception.getResponse();
    }

    @Override
    @ExceptionHandler(PaymentNotFoundException.class)
    public ResponseEntity<ExceptionResponseDTO> notFound(NotFoundException exception) {
        return exception.getResponse();
    }

    @Override
    public ResponseEntity<ExceptionResponseDTO> invalidValue(InvalidValueException exception) {
        throw new UnsupportedOperationException("Unimplemented method 'invalidValue'");
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
    public ResponseEntity<ExceptionResponseDTO> forbidden(ForbiddenException exception) {
        throw new UnsupportedOperationException("Unimplemented method 'forbidden'");
    }
    
}
