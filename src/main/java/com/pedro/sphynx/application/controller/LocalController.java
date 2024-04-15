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

import java.util.List;

@RestController
@RequestMapping("local")
public class LocalController {

    @Autowired
    private LocalRepository repository;

    @Autowired
    private LocalService service;

    @PostMapping
    @Transactional
    public ResponseEntity create(@RequestBody @Valid LocalDataInput data, @RequestHeader("Language") String language){
        var local = service.createVerify(data, language);

        return ResponseEntity.ok(local);
    }

    @PutMapping("/{name}")
    @Transactional
    public ResponseEntity update(@PathVariable String name, @RequestBody @Valid LocalDataEditInput data, @RequestHeader("Language") String language){
        var local = service.updateVerify(data, name, language);

        return ResponseEntity.ok(local);
    }

    @GetMapping
    public ResponseEntity<List> get(){
        var localsList = repository.findAll();

        return ResponseEntity.ok(localsList);
    }

    @DeleteMapping("/{name}")
    @Transactional
    public ResponseEntity delete(@PathVariable String name){
        repository.deleteByName(name);

        return ResponseEntity.noContent().build();
    }
}
