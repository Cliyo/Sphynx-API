package com.pedro.sphynx.application.controller;

import com.pedro.sphynx.application.dtos.consumer.ConsumerDataComplete;
import com.pedro.sphynx.application.dtos.consumer.ConsumerDataEditInput;
import com.pedro.sphynx.application.dtos.consumer.ConsumerDataInput;
import com.pedro.sphynx.application.dtos.group.GroupDataComplete;
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
import java.util.Optional;

@RestController
@RequestMapping("consumers")
public class ConsumerController{

    @Autowired
    private ConsumerService service;

    @Autowired
    private MessageService messageService;

    @PostMapping
    @Transactional
    public ResponseEntity create(@RequestBody @Valid ConsumerDataInput data, @RequestHeader("Language") String language){
        var consumerDto = service.create(data, language);
        MessageDTO dto = messageService.createMessage(201, consumerDto, language);

        return ResponseEntity.ok(dto);

    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity update(@PathVariable String id, @RequestBody @Valid ConsumerDataEditInput data, @RequestHeader("Language") String language){
        var consumerDto = service.update(data, id, language);
        MessageDTO dto = messageService.createMessage(200, consumerDto, language);

        return ResponseEntity.ok(dto);
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity delete(@PathVariable String id, @RequestHeader("Language") String language){
        service.delete(Long.parseLong(id), language);

        return ResponseEntity.noContent().build();
    }


    @GetMapping
    public ResponseEntity<List<ConsumerDataComplete>> getAll(@RequestParam("group") Optional<String> group){

        var listConsumers = service.getAll(group);

        return ResponseEntity.ok(listConsumers);
    }

    @GetMapping("/{id}")
    public ResponseEntity getById(@PathVariable String id, @RequestHeader("Language") String language) {

        var consumer = service.getById(Long.parseLong(id), language);

        return ResponseEntity.ok(consumer);
    }
}
