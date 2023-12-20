package com.example.wedding_gifts.infra.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.example.wedding_gifts.core.domain.dtos.exception.ExceptionResponseDTO;
import com.example.wedding_gifts.core.domain.exceptions.common.ExecutionException;
import com.example.wedding_gifts.core.domain.exceptions.common.ForbiddenException;
import com.example.wedding_gifts.core.domain.exceptions.common.InvalidValueException;
import com.example.wedding_gifts.core.domain.exceptions.common.NotFoundException;
import com.example.wedding_gifts.core.domain.exceptions.common.NotNullableException;
import com.example.wedding_gifts.core.domain.exceptions.common.NotYourException;
import com.example.wedding_gifts.core.domain.exceptions.image.ImageExecutionException;
import com.example.wedding_gifts.core.domain.exceptions.image.ImageInvalidValueException;
import com.example.wedding_gifts.core.domain.exceptions.image.ImageNotFoundException;
import com.example.wedding_gifts.core.domain.exceptions.image.ImageNotNullableException;
import com.example.wedding_gifts.core.domain.exceptions.image.ImageNotYourException;
import com.example.wedding_gifts.core.usecases.exception.IExceptionResponse;

@RestControllerAdvice
public class ImageExceptionHandler implements IExceptionResponse {

    @Override
    @ExceptionHandler(ImageExecutionException.class)
    public ResponseEntity<ExceptionResponseDTO> execution(ExecutionException exception) {
        return exception.getResponse();
    }

    @Override
    @ExceptionHandler(ImageInvalidValueException.class)
    public ResponseEntity<ExceptionResponseDTO> invalidValue(InvalidValueException exception) {
        return exception.getResponse();
    }

    @Override
    @ExceptionHandler(ImageNotFoundException.class)
    public ResponseEntity<ExceptionResponseDTO> notFound(NotFoundException exception) {
        return exception.getResponse();
    }

    @Override
    @ExceptionHandler(ImageNotNullableException.class)
    public ResponseEntity<ExceptionResponseDTO> notNullable(NotNullableException exception) {
        return exception.getResponse();
    }

    @Override
    @ExceptionHandler(ImageNotYourException.class)
    public ResponseEntity<ExceptionResponseDTO> notYour(NotYourException exception) {
        return exception.getResponse();
    }

    @Override
    public ResponseEntity<ExceptionResponseDTO> forbidden(ForbiddenException exception) {
        throw new UnsupportedOperationException("Unimplemented method 'forbidden'");
    }
    
}