package com.pedro.sphynx.infrastructure.exceptions;

import com.pedro.sphynx.application.dtos.message.MessageDTO;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@RestControllerAdvice
public class ErrorExceptions {
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity entityNotFound(EntityNotFoundException e){
        return ResponseEntity.badRequest().body(new MessageDTO(400, e.getMessage(), null));
    }

    @ExceptionHandler(EntityExistsException.class)
    public ResponseEntity entityExists(EntityExistsException e){
        return ResponseEntity.badRequest().body(new MessageDTO(400, e.getMessage(), null));
    }

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity noSuchElement(){
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(Validation.class)
    public ResponseEntity validation(Validation e){
        return ResponseEntity.badRequest().body(new MessageDTO(400, e.getMessage(), null));
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity auth(UsernameNotFoundException e){
        return ResponseEntity.status(401).body(new MessageDTO(401, "Usuario ou senha incorretos.", null));
    }

    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public ResponseEntity sqlIntegrity(){
        return ResponseEntity.badRequest().body(new MessageDTO(400, "Exclusao nao autorizada.", null));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity methodArgumentNotValid(MethodArgumentNotValidException ex){
        List<String> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(fieldError -> fieldError.getDefaultMessage())
                .collect(Collectors.toList());

        // Create error response
        MessageDTO errorResponse = new MessageDTO(
                400,
                errors.toString(),
                null
        );

        System.out.println(errors);

        return ResponseEntity.badRequest().body(errorResponse);
    }

}
