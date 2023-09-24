package com.example.exceptions.models;

import org.springframework.http.HttpStatus;

public class NotFoundException extends ApplicationException {
    public NotFoundException(String message) {
        super(message, HttpStatus.NOT_FOUND);
    }
}
