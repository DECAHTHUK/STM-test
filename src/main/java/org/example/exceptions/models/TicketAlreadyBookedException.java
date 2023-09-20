package org.example.exceptions.models;

import org.springframework.http.HttpStatus;

public class TicketAlreadyBookedException extends ApplicationException{
    public TicketAlreadyBookedException(String message) {
        super(message, HttpStatus.BAD_REQUEST);
    }
}
