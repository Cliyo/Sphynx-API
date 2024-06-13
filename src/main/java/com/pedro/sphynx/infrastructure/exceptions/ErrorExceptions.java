package com.pedro.sphynx.infrastructure.exceptions;

import com.pedro.sphynx.application.dtos.message.MessageDTO;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLIntegrityConstraintViolationException;
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

}
