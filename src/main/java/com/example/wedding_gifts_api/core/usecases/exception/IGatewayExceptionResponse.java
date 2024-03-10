package com.example.wedding_gifts_api.core.usecases.exception;

import org.springframework.http.ResponseEntity;

import com.example.wedding_gifts_api.core.domain.exceptions.common.ExecutionException;
import com.example.wedding_gifts_api.core.domain.exceptions.common.GatewayException;
import com.example.wedding_gifts_api.infra.dtos.exception.ExceptionResponseDTO;

public abstract class IGatewayExceptionResponse {

    public ResponseEntity<ExceptionResponseDTO> badGateway(GatewayException exception) throws Exception {
        throw new UnsupportedOperationException("ExceptionHandler not implemented");
    }

    public ResponseEntity<ExceptionResponseDTO> certificate(ExecutionException exception) throws Exception {
        throw new UnsupportedOperationException("ExceptionHandler not implemented");
    }

    public ResponseEntity<ExceptionResponseDTO> http(ExecutionException exception) throws Exception {
        throw new UnsupportedOperationException("ExceptionHandler not implemented");
    }

}
