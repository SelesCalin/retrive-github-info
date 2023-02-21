package com.example.githubretrieve.exception;

import java.io.Serial;

public class UserNotFoundException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = 123456789L;

    public UserNotFoundException() {
        super();
    }

}
