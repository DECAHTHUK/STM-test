package com.example.exceptions.models;

import org.springframework.http.HttpStatus;

public class CreationException  extends ApplicationException {
    public CreationException(String message) {
        super(message, HttpStatus.BAD_REQUEST);
    }
}
