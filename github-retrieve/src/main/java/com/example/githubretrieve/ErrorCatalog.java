package com.example.githubretrieve;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum ErrorCatalog {

    WRONG_ACCEPT_HEADER(new CustomError(HttpStatus.NOT_ACCEPTABLE.value(), "Accept header not supported")),
    WRONG_USERNAME(new CustomError(HttpStatus.BAD_REQUEST.value(), "No user with the specified username found"));

    private final CustomError error;
}
