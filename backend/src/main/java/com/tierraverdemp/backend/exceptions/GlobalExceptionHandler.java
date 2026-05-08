package com.tierraverdemp.backend.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleFailedValidationException(MethodArgumentNotValidException e){
        Map<String, String> errorMap = new HashMap<>();
        e.getBindingResult().getFieldErrors().forEach(error ->
            errorMap.put(error.getField(), error.getDefaultMessage())
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMap);
    }
    @ExceptionHandler({EmailAlreadyRegisteredException.class, UsernameAlreadyRegistered.class})
    public ResponseEntity<?> handleConflictException(RuntimeException e){

        String field;
        if (e instanceof EmailAlreadyRegisteredException) {
            field = "email";
        } else {
            field = "username";
        }
        return ResponseEntity.status(HttpStatus.CONFLICT).body(
                Map.of(
                        "field", field,
                        "message", e.getMessage()
                )
        );
    }

    @ExceptionHandler({BadCredentialsException.class})
    public ResponseEntity<?> handleLoginFailedException(BadCredentialsException e){
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
                Map.of("message", e.getMessage())
        );
    }
}
