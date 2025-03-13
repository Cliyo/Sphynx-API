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
    public ResponseEntity<MessageDTO> create(@RequestBody @Valid ConsumerDataInput data){
        ConsumerDataComplete consumerDto = service.create(data);
        MessageDTO messageDto = messageService.createMessage(201, consumerDto);

        return ResponseEntity.ok(messageDto);

    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<MessageDTO> update(@PathVariable String id, @RequestBody @Valid ConsumerDataEditInput data){
        var consumerDto = service.update(data, Long.parseLong(id));
        MessageDTO messageDto = messageService.createMessage(200, consumerDto);

        return ResponseEntity.ok(messageDto);
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity delete(@PathVariable String id){
        service.delete(Long.parseLong(id));

        return ResponseEntity.noContent().build();
    }


    @GetMapping
    public ResponseEntity<MessageDTO> getAll(@RequestParam("group") Optional<String> group){

        var listConsumers = service.getAll(group);
        MessageDTO messageDto = messageService.createMessage(200, listConsumers);

        return ResponseEntity.ok(messageDto);
    }

    @GetMapping("/{id}")
    public ResponseEntity getById(@PathVariable String id) {

        var consumer = service.getById(Long.parseLong(id));
        MessageDTO messageDTO = messageService.createMessage(200, consumer);

        return ResponseEntity.ok(messageDTO);
    }
}
