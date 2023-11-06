package com.pedro.sphynx.application.controller;

import com.pedro.sphynx.application.dtos.consumer.ConsumerDataInputDTO;
import com.pedro.sphynx.infrastructure.entities.Consumer;
import com.pedro.sphynx.infrastructure.repository.ConsumerRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("consumer")
public class ConsumerController {

    @Autowired
    private ConsumerRepository repository;

    @PostMapping
    @Transactional
    public ResponseEntity create(@RequestBody @Valid ConsumerDataInputDTO data){
        Consumer consumer = new Consumer(data);

        repository.save(consumer);

        return ResponseEntity.ok().build();
    }
}
