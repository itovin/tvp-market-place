package com.tierraverdemp.backend.exceptions;

public class UsernameAlreadyRegistered extends RuntimeException {
    public UsernameAlreadyRegistered(String message) {
        super(message);
    }
}
