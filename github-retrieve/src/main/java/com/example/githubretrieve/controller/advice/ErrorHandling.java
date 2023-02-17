package com.example.githubretrieve.controller.advice;

import com.example.githubretrieve.CustomError;
import com.example.githubretrieve.ErrorCatalog;
import com.example.githubretrieve.exception.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ErrorHandling {

    @ExceptionHandler(HttpMediaTypeNotAcceptableException.class)
    public ResponseEntity<CustomError> handleNotAcceptableStatusException() {
        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE)
                .contentType(MediaType.APPLICATION_JSON)
                .body(ErrorCatalog.WRONG_ACCEPT_HEADER.getError());
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<CustomError> handleNotFoundUser() {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .contentType(MediaType.APPLICATION_JSON)
                .body(ErrorCatalog.WRONG_USERNAME.getError());
    }
}
