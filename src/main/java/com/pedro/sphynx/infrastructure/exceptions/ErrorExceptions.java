package com.pedro.sphynx.infrastructure.exceptions;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.NoSuchElementException;

@RestControllerAdvice
public class ErrorExceptions {
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity entityNotFound(){
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity noSuchElement(){
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(Validation.class)
    public ResponseEntity validation(Validation e){
        return ResponseEntity.badRequest().body(new Error(e.getMessage()));
    }
}
