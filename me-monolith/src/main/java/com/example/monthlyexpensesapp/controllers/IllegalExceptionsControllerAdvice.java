package com.example.monthlyexpensesapp.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(annotations = IllegalExceptionProcessing.class)
public class IllegalExceptionsControllerAdvice {

    @ExceptionHandler(IllegalArgumentException.class)
    ResponseEntity<String> illegalArgHandler(IllegalArgumentException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    @ExceptionHandler(IllegalStateException.class)
    ResponseEntity<String> illegalStateHandler(IllegalStateException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }

}
