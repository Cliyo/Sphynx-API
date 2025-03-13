package com.pedro.sphynx.application.controller;

import com.pedro.sphynx.application.dtos.local.LocalDataEditInput;
import com.pedro.sphynx.application.dtos.local.LocalDataInput;
import com.pedro.sphynx.application.dtos.localGroup.LocalGroupDataComplete;
import com.pedro.sphynx.application.dtos.message.MessageDTO;
import com.pedro.sphynx.domain.LocalService;
import com.pedro.sphynx.domain.MessageService;
import com.pedro.sphynx.infrastructure.repository.LocalRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("locals")
public class LocalController{

    @Autowired
    private LocalService service;

    @Autowired
    private MessageService messageService;

    @PostMapping
    @Transactional
    public ResponseEntity<MessageDTO> create(@RequestBody @Valid LocalDataInput data){
        var local = service.create(data);
        MessageDTO messageDTO = messageService.createMessage(201, local);

        return ResponseEntity.ok(messageDTO);
    }

    @PutMapping("/{name}")
    @Transactional
    public ResponseEntity<MessageDTO> update(@PathVariable String id, @RequestBody @Valid LocalDataEditInput data){
        var local = service.update(data, Integer.parseInt(id));
        MessageDTO messageDTO = messageService.createMessage(200, local);

        return ResponseEntity.ok(messageDTO);
    }

    @GetMapping
    public ResponseEntity<MessageDTO> getAll(){
        List<LocalGroupDataComplete> localsList = service.getAllLocalsWithGroups();
        MessageDTO messageDTO = messageService.createMessage(200, localsList);

        return ResponseEntity.ok(messageDTO);
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity delete(@PathVariable String id){
        service.deleteById(Long.parseLong(id));

        return ResponseEntity.noContent().build();
    }
}
