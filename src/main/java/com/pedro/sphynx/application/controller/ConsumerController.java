package com.pedro.sphynx.application.controller;

import com.pedro.sphynx.application.dtos.consumer.ConsumerDataComplete;
import com.pedro.sphynx.application.dtos.consumer.ConsumerDataEditInputDTO;
import com.pedro.sphynx.application.dtos.consumer.ConsumerDataInputDTO;
import com.pedro.sphynx.application.dtos.person.PersonDataComplete;
import com.pedro.sphynx.domain.ConsumerService;
import com.pedro.sphynx.infrastructure.entities.Consumer;
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
    private ConsumerRepository consumerRepository;

    @Autowired
    private PersonRepository personRepository;

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
        consumerRepository.deleteByRa(ra);

        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<ConsumerDataComplete>> get(){
        List<ConsumerDataComplete> finalList = new ArrayList<>();
        var listConsumers = consumerRepository.findAll().stream().map(ConsumerDataComplete::new).toList();

        System.out.println(consumerRepository.findAll().stream().map(ConsumerDataComplete::new).toList());

        for(ConsumerDataComplete dto : listConsumers){
            var person = new PersonDataComplete(personRepository.findOneByRa(dto.ra()));
            var finalDto = dto.withName(dto, person.name());

            finalList.add(finalDto);
        }
        return ResponseEntity.ok(finalList);
    }
}
