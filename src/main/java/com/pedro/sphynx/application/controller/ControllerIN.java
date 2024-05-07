package com.pedro.sphynx.application.controller;

import org.springframework.http.ResponseEntity;

public interface ControllerIN<C, U>{
    public ResponseEntity create(C data, String language);

    public ResponseEntity update(String id, U data, String language);
    public ResponseEntity delete(String id);
    public ResponseEntity get();
}
