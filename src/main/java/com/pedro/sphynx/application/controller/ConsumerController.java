package com.pedro.sphynx.application.controller;

import com.pedro.sphynx.application.dtos.consumer.ConsumerDataComplete;
import com.pedro.sphynx.application.dtos.consumer.ConsumerDataEditInputDTO;
import com.pedro.sphynx.application.dtos.consumer.ConsumerDataInputDTO;
import com.pedro.sphynx.domain.ConsumerService;
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

    @Autowired
    private ConsumerService service;

    @PostMapping
    @Transactional
    public ResponseEntity create(@RequestBody @Valid ConsumerDataInputDTO data){
        var consumerDto = service.createVerify(data);

        return ResponseEntity.ok(consumerDto);
    }

    @PutMapping("/{ra}")
    @Transactional
    public ResponseEntity update(@PathVariable String ra, @RequestBody @Valid ConsumerDataEditInputDTO data){
        var consumerDto = service.updateVerify(data, ra);

        return ResponseEntity.ok(consumerDto);
    }

    @DeleteMapping("/{ra}")
    @Transactional
    public ResponseEntity delete(@PathVariable String ra){
        repository.deleteByRa(ra);

        return ResponseEntity.noContent().build();
    }
}
