package com.pedro.sphynx.application.controller;

import com.pedro.sphynx.application.dtos.consumer.ConsumerDataComplete;
import com.pedro.sphynx.application.dtos.consumer.ConsumerDataEditInput;
import com.pedro.sphynx.application.dtos.consumer.ConsumerDataInput;
import com.pedro.sphynx.application.dtos.message.MessageDTO;
import com.pedro.sphynx.domain.ConsumerService;
import com.pedro.sphynx.domain.MessageService;
import com.pedro.sphynx.infrastructure.repository.ConsumerRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("consumer")
public class ConsumerController {

    @Autowired
    private ConsumerRepository repository;

    @Autowired
    private ConsumerService service;

    @Autowired
    private MessageService messageService;

    @PostMapping
    @Transactional
    public ResponseEntity create(@RequestBody @Valid ConsumerDataInput data, @RequestHeader("Language") String language){
        var consumerDto = service.createVerify(data, language);
        MessageDTO dto = messageService.createMessage(201, consumerDto, language);

        return ResponseEntity.ok(dto);

    }

    @PutMapping("/{ra}")
    @Transactional
    public ResponseEntity update(@PathVariable String ra, @RequestBody @Valid ConsumerDataEditInput data, @RequestHeader("Language") String language){
        var consumerDto = service.updateVerify(data, ra, language);
        MessageDTO dto = messageService.createMessage(200, consumerDto, language);

        return ResponseEntity.ok(dto);
    }

    @DeleteMapping("/{ra}")
    @Transactional
    public ResponseEntity delete(@PathVariable String ra){
        repository.deleteByRa(ra);

        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<ConsumerDataComplete>> get(){
        var listConsumers = repository.findAll().stream().map(ConsumerDataComplete::new).toList();

        return ResponseEntity.ok(listConsumers);
    }
}
