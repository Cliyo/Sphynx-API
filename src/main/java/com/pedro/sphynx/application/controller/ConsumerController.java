package com.pedro.sphynx.application.controller;

import com.pedro.sphynx.application.dtos.consumer.ConsumerDataComplete;
import com.pedro.sphynx.application.dtos.consumer.ConsumerDataEditInput;
import com.pedro.sphynx.application.dtos.consumer.ConsumerDataInput;
import com.pedro.sphynx.application.dtos.consumerGroup.ConsumerGroupDataComplete;
import com.pedro.sphynx.application.dtos.message.MessageDTO;
import com.pedro.sphynx.domain.ConsumerService;
import com.pedro.sphynx.domain.MessageService;
import com.pedro.sphynx.infrastructure.entities.ConsumerGroup;
import com.pedro.sphynx.infrastructure.exceptions.Validation;
import com.pedro.sphynx.infrastructure.repository.ConsumerGroupRepository;
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

    @Autowired
    private ConsumerGroupRepository consumerGroupRepository;

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
    public ResponseEntity<List<ConsumerGroupDataComplete>> get(@RequestParam("group") Optional<String> group){

        List<ConsumerGroupDataComplete> listConsumers;

        if(group.isPresent()){
            listConsumers = consumerGroupRepository.findAllByGroupName(group.get()).stream().map(ConsumerGroupDataComplete::new).toList();

        }

        else{
            listConsumers = consumerGroupRepository.findAll().stream().map(ConsumerGroupDataComplete::new).toList();
        }

        return ResponseEntity.ok(listConsumers);
    }
}
