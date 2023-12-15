package com.pedro.sphynx.application.controller;

import com.pedro.sphynx.application.dtos.local.LocalDataEditInput;
import com.pedro.sphynx.application.dtos.local.LocalDataInput;
import com.pedro.sphynx.domain.LocalService;
import com.pedro.sphynx.infrastructure.entities.Local;
import com.pedro.sphynx.infrastructure.repository.LocalRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("locals")
public class LocalController {

    @Autowired
    private LocalRepository repository;

    @Autowired
    private LocalService service;

    @PostMapping
    @Transactional
    public ResponseEntity create(@RequestBody @Valid LocalDataInput data){
        var local = service.createVerify(data);

        return ResponseEntity.ok(local);
    }

    @PutMapping("/{name}")
    @Transactional
    public ResponseEntity update(@PathVariable String name, @RequestBody @Valid LocalDataEditInput data){
        var local = repository.getReferenceByName(name);

        local.updateLocal(data);

        return ResponseEntity.ok(local);
    }
}
