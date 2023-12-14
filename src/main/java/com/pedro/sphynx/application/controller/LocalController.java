package com.pedro.sphynx.application.controller;

import com.pedro.sphynx.application.dtos.local.LocalDataInput;
import com.pedro.sphynx.infrastructure.entities.Local;
import com.pedro.sphynx.infrastructure.repository.LocalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("locals")
public class LocalController {

    @Autowired
    private LocalRepository repository;

    @PostMapping
    @Transactional
    public ResponseEntity create(@RequestBody LocalDataInput data){
        repository.save(new Local(data));

        return ResponseEntity.ok().build();
    }
}
