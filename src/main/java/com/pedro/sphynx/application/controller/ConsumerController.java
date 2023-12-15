package com.pedro.sphynx.application.controller;

import com.pedro.sphynx.application.dtos.consumer.ConsumerDataComplete;
import com.pedro.sphynx.application.dtos.consumer.ConsumerDataEditInput;
import com.pedro.sphynx.application.dtos.consumer.ConsumerDataInput;
import com.pedro.sphynx.domain.ConsumerService;
import com.pedro.sphynx.infrastructure.repository.ConsumerRepository;
import com.pedro.sphynx.infrastructure.repository.PersonRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("consumer")
public class ConsumerController {

    @Autowired
    private ConsumerRepository repository;

    @Autowired
    private ConsumerService service;

    @PostMapping
    @Transactional
    public ResponseEntity create(@RequestBody @Valid ConsumerDataInput data){
        var consumerDto = service.createVerify(data);

        return ResponseEntity.ok(consumerDto);
    }

    @PutMapping("/{ra}")
    @Transactional
    public ResponseEntity update(@PathVariable String ra, @RequestBody @Valid ConsumerDataEditInput data){
        var consumerDto = service.updateVerify(data, ra);

        return ResponseEntity.ok(consumerDto);
    }

    @DeleteMapping("/{ra}")
    @Transactional
    public ResponseEntity delete(@PathVariable String ra){
        repository.deleteByPersonRa(ra);

        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<ConsumerDataComplete>> get(){
        var listConsumers = repository.findAll().stream().map(ConsumerDataComplete::new).toList();

        return ResponseEntity.ok(listConsumers);
    }
}
