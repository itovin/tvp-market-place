package com.tierraverdemp.backend.exceptions;

public class LoginFailedException extends RuntimeException {
    public LoginFailedException(String userNotFound) {
    }
}
