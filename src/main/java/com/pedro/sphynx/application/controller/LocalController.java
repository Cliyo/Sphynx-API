package com.pedro.sphynx.application.controller;

import com.pedro.sphynx.application.dtos.local.LocalDataEditInput;
import com.pedro.sphynx.application.dtos.local.LocalDataInput;
import com.pedro.sphynx.application.dtos.localGroup.LocalGroupDataComplete;
import com.pedro.sphynx.application.dtos.message.MessageDTO;
import com.pedro.sphynx.domain.LocalService;
import com.pedro.sphynx.domain.MessageService;
import com.pedro.sphynx.infrastructure.entities.Local;
import com.pedro.sphynx.infrastructure.repository.LocalGroupRepository;
import com.pedro.sphynx.infrastructure.repository.LocalRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("locals")
public class LocalController implements ControllerIN<LocalDataInput, LocalDataEditInput>{

    @Autowired
    private LocalRepository repository;

    @Autowired
    private LocalGroupRepository localGroupRepository;

    @Autowired
    private LocalService service;

    @Autowired
    private MessageService messageService;

    @Override
    @PostMapping
    @Transactional
    public ResponseEntity create(@RequestBody @Valid LocalDataInput data, @RequestHeader("Language") String language){
        var local = service.createVerify(data, language);
        MessageDTO dto = messageService.createMessage(201, local, language);

        return ResponseEntity.ok(dto);
    }

    @Override
    @PutMapping("/{name}")
    @Transactional
    public ResponseEntity update(@PathVariable String name, @RequestBody @Valid LocalDataEditInput data, @RequestHeader("Language") String language){
        var local = service.updateVerify(data, name, language);
        MessageDTO dto = messageService.createMessage(200, local, language);

        return ResponseEntity.ok(dto);
    }

    @Override
    @GetMapping
    public ResponseEntity<List> get(){
        List<LocalGroupDataComplete> localsList = localGroupRepository.findAll().stream().map(LocalGroupDataComplete::new).toList();

        return ResponseEntity.ok(localsList);
    }

    @Override
    @DeleteMapping("/{name}")
    @Transactional
    public ResponseEntity delete(@PathVariable String name){
        repository.deleteByName(name);

        return ResponseEntity.noContent().build();
    }
}
