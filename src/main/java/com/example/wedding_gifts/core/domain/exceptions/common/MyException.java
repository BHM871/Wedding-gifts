package com.example.wedding_gifts.core.domain.exceptions.common;

import java.time.LocalDateTime;

import org.springframework.http.ResponseEntity;

import com.example.wedding_gifts.core.domain.dtos.exception.ExceptionResponseDTO;

public abstract class MyException extends Exception {

    private Throwable cause;

    protected Integer statusCode;
    protected String exception;
    protected String message;
    protected String path;

    public MyException(
        Throwable cause,
        Integer statusCode,
        String exception,
        String message,
        String path
    ) {
        this.cause = cause;
        this.statusCode = statusCode;
        this.exception = exception;
        this.message = message;
        this.path = path;
    }
    
    public MyException(
        Throwable cause, 
        Integer statusCode, 
        String exception, 
        String message
    ) {
        this(cause, 400, exception, "Exception", null);    
    }

    public MyException(
        Throwable cause, 
        String exception, 
        String message
    ) {
        this(cause, 400, exception, message);    
    }

    public MyException(
        Throwable cause, 
        String message
    ) {
        this(cause, 400, "MyException.class", message, null);  
    }

    public MyException(
        Integer statusCode,
        String exception,
        String message,
        String path
    ) {
        this(new Throwable(), statusCode, exception, message, path);
    }

    public MyException(
        Integer statusCode,
        String exception,
        String message
    ) {
        this(statusCode, exception, message, null);
    }

    public MyException(
        Integer statusCode,
        String exception
    ) {
        this(statusCode, exception, "Exception");
    }

    public MyException(
        Integer statusCode
    ) {
        this(statusCode, "MyException.class");
    }

    public MyException() {
        this(400);
    }

    public ResponseEntity<ExceptionResponseDTO> getResponse() {
        return ResponseEntity.status(statusCode).body(
            new ExceptionResponseDTO(
                LocalDateTime.now(), 
                statusCode, 
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
