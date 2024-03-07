package com.example.wedding_gifts.core.domain.exceptions.common;

import java.time.LocalDateTime;

import org.springframework.http.ResponseEntity;

import com.example.wedding_gifts.common.MyZone;
import com.example.wedding_gifts.infra.dtos.exception.ExceptionResponseDTO;

public abstract class MyException extends Exception {

    private Throwable cause;

    protected Integer statusCode;
    protected String error;
    protected String exception;
    protected String message;
    protected String path;

    public MyException(
        Throwable cause,
        Integer statusCode,
        String error,
        String exception,
        String message,
        String path
    ) {
        this.cause = cause;
        this.statusCode = statusCode;
        this.error = error;
        this.exception = exception;
        this.message = message;
        this.path = path;
    }
    
    public MyException(
        Throwable cause, 
        Integer statusCode,
        String error,
        String exception, 
        String message
    ) {
        this(cause, statusCode, error, exception, message, "Undefined");    
    }

    public MyException(
        Throwable cause,
        String exception, 
        String message
    ) {
        this(cause, 400, "Bad Request", exception, message);    
    }

    public MyException(
        Throwable cause, 
        String message
    ) {
        this(cause, "MyException.class", message);  
    }

    public MyException(
        Integer statusCode,
        String error,
        String exception,
        String message,
        String path
    ) {
        this(new Throwable(), statusCode, error, exception, message, path);
    }

    public MyException(
        Integer statusCode,
        String error,
        String exception,
        String message
    ) {
        this(statusCode, error, exception, message, "Undefined");
    }

    public MyException(
        Integer statusCode,
        String error,
        String exception
    ) {
        this(statusCode, error, exception, "Exception");
    }

    public MyException(
        Integer statusCode,
        String error
    ) {
        this(statusCode, error, "MyException.class");
    }

    public MyException() {
        this(400, "Bad Request");
    }

    public ResponseEntity<ExceptionResponseDTO> getResponse() {
        return ResponseEntity.status(statusCode).body(
            new ExceptionResponseDTO(
                LocalDateTime.now(MyZone.zoneId()), 
                statusCode,
                error,
                exception, 
                message, 
                path
            )
        );
    }

    public int getStatusCode() {
        return statusCode;
    }

    public String getException() {
        return exception;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public String getPath() {
        return path;
    }

    public Throwable getCause() {
        return cause;
    }

    public void setStatusCode(Integer statusCode) {
        this.statusCode = statusCode;
    }

    public void setExceptionName(String exception) {
        this.exception = exception;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getThowable(){
        return cause.toString();
    }
    
}
