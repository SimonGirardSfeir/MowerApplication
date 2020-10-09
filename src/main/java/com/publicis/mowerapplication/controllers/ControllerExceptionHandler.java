package com.publicis.mowerapplication.controllers;

import com.publicis.mowerapplication.exceptions.ApiError;
import com.publicis.mowerapplication.exceptions.IncorrectContentException;
import com.publicis.mowerapplication.exceptions.IncorrectFileNameException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ControllerExceptionHandler extends ResponseEntityExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(IncorrectFileNameException.class)
    public ResponseEntity<Object> handleIncorrectFileNameException(IncorrectFileNameException e) {
        ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, e.getMessage());
        return new ResponseEntity<>(apiError, new HttpHeaders(), apiError.getHttpStatus());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(IncorrectContentException.class)
    public ResponseEntity<Object> handleIncorrectContentException(IncorrectContentException e) {
        ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, e.getMessage());
        return new ResponseEntity<>(apiError, new HttpHeaders(), apiError.getHttpStatus());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(NumberFormatException.class)
    public ResponseEntity<Object> handleNumberFormatException() {
        ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, "Number format exception.");
        return new ResponseEntity<>(apiError, new HttpHeaders(), apiError.getHttpStatus());
    }

}
