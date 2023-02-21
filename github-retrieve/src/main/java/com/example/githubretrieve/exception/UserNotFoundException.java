package com.example.githubretrieve.exception;

import java.io.Serial;
import java.io.Serializable;

public class UserNotFoundException extends RuntimeException implements Serializable {

    @Serial
    private static final long serialVersionUID = -6165718301762435365L;

    public UserNotFoundException() {
        super();
    }

}
