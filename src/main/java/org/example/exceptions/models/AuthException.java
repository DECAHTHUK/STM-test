package org.example.exceptions.models;

import org.springframework.http.HttpStatus;

public class AuthException extends ApplicationException{
    public AuthException(String message) {
        super(message, HttpStatus.UNAUTHORIZED);
    }
}
