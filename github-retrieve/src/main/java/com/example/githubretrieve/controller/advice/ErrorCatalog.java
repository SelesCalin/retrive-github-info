package com.example.githubretrieve.controller.advice;

import com.example.githubretrieve.dto.ErrorResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum ErrorCatalog {

    WRONG_ACCEPT_HEADER(new ErrorResponse(HttpStatus.NOT_ACCEPTABLE.value(), "Accept header not supported")),
    WRONG_USERNAME(new ErrorResponse(HttpStatus.BAD_REQUEST.value(), "No user with the specified username found"));

    private final ErrorResponse error;
}
