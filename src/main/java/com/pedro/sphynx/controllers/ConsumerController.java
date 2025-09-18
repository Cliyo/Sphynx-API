package com.pedro.sphynx.controllers;

import com.pedro.sphynx.dtos.consumer.ConsumerDataComplete;
import com.pedro.sphynx.dtos.consumer.ConsumerDataEditInput;
import com.pedro.sphynx.dtos.consumer.ConsumerDataInput;
import com.pedro.sphynx.dtos.message.MessageDTO;
import com.pedro.sphynx.entities.User;
import com.pedro.sphynx.services.ConsumerService;
import com.pedro.sphynx.utils.CreateMessageUtil;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("consumers")
public class ConsumerController{

    @Autowired
    private ConsumerService service;

    @Autowired
    private CreateMessageUtil createMessageUtil;

    @PostMapping
    @Transactional
    public ResponseEntity<MessageDTO> create(@RequestBody @Valid ConsumerDataInput data, @AuthenticationPrincipal UserDetails userDetails){
        ConsumerDataComplete consumerDto = service.create(data, (User) userDetails);
        MessageDTO messageDto = createMessageUtil.createMessage(201, consumerDto);

        return ResponseEntity.ok(messageDto);

    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<MessageDTO> update(@PathVariable String id, @RequestBody @Valid ConsumerDataEditInput data){
        var consumerDto = service.update(data, Long.parseLong(id));
        MessageDTO messageDto = createMessageUtil.createMessage(200, consumerDto);

        return ResponseEntity.ok(messageDto);
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<Void> delete(@PathVariable String id){
        service.delete(Long.parseLong(id));

        return ResponseEntity.noContent().build();
    }


    @GetMapping
    public ResponseEntity<MessageDTO> getAll(@RequestParam("ra") Optional<String> ra, @AuthenticationPrincipal UserDetails userDetails){
        User loggedUser = (User) userDetails;

        var listConsumers = ra.isPresent() ? service.getAllByRa(ra.get(), loggedUser) : service.getAll(loggedUser);
        MessageDTO messageDto = createMessageUtil.createMessage(200, listConsumers);

        return ResponseEntity.ok(messageDto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MessageDTO> getById(@PathVariable String id) {

        var consumer = service.getById(Long.parseLong(id));
        MessageDTO messageDTO = createMessageUtil.createMessage(200, consumer);

        return ResponseEntity.ok(messageDTO);
    }
}
