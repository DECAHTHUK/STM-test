package org.example.exceptions;

import org.example.exceptions.models.ApplicationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.List;

@RestControllerAdvice
public class ExceptionHandler {

    @org.springframework.web.bind.annotation.ExceptionHandler(ApplicationException.class)
    public ResponseEntity<Object> handleApplicationException(ApplicationException ex) {
        return new ResponseEntity<>(ex.getMessage(), ex.getStatus());
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        return new ResponseEntity<>(parseFieldErrors(ex.getFieldErrors()), HttpStatus.BAD_REQUEST);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<Object> handleMethodArgumentNotValidException(MissingServletRequestParameterException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<Object> handleMethodArgumentNotValidException(MethodArgumentTypeMismatchException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Object> handleMethodArgumentNotValidException(HttpMessageNotReadableException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    private String parseFieldErrors(List<FieldError> fieldErrors) {
        StringBuilder res = new StringBuilder();
        int cnt = 1;
        for (FieldError fieldError : fieldErrors) {
            res.append(cnt).append(". ").append(fieldError.getDefaultMessage()).append("\n");
            cnt++;
        }
        return res.toString();
    }
}