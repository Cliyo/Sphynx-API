package com.pedro.sphynx.application.controller;

import com.pedro.sphynx.application.dtos.consumer.ConsumerDataComplete;
import com.pedro.sphynx.application.dtos.consumer.ConsumerDataEditInput;
import com.pedro.sphynx.application.dtos.consumer.ConsumerDataInput;
import com.pedro.sphynx.application.dtos.message.MessageDTO;
import com.pedro.sphynx.domain.ConsumerService;
import com.pedro.sphynx.domain.MessageService;
import com.pedro.sphynx.infrastructure.exceptions.Validation;
import com.pedro.sphynx.infrastructure.repository.ConsumerRepository;
import jakarta.validation.Valid;
import lombok.Builder;
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
    public ResponseEntity delete(@PathVariable String ra, @RequestHeader("Language") String language){
        service.deleteVerify(ra, language);

        return ResponseEntity.noContent().build();
    }


    @GetMapping
    public ResponseEntity<List<ConsumerDataComplete>> get(@RequestParam("permission") Optional<Integer> permission){

        List<ConsumerDataComplete> listConsumers;

        if(permission.isPresent()){
            listConsumers = repository.findAllByPermission_Level(permission.get()).stream().map(ConsumerDataComplete::new).toList();

        }

        else{
            listConsumers = repository.findAll().stream().map(ConsumerDataComplete::new).toList();
        }

        return ResponseEntity.ok(listConsumers);
    }
}
