package com.example.githubretrieve.exception;

import java.io.Serial;

public class UserNotFoundException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = -6165718301762435365L;

    public UserNotFoundException() {
        super();
    }

}
