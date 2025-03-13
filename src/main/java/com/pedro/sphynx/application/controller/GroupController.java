package com.pedro.sphynx.application.controller;

import com.pedro.sphynx.application.dtos.group.GroupDataEdit;
import com.pedro.sphynx.application.dtos.message.MessageDTO;
import com.pedro.sphynx.application.dtos.group.GroupDataComplete;
import com.pedro.sphynx.application.dtos.group.GroupDataInput;
import com.pedro.sphynx.domain.MessageService;
import com.pedro.sphynx.domain.GroupService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("groups")
public class GroupController {

    @Autowired
    private GroupService service;

    @Autowired
    private MessageService messageService;

    @PostMapping
    @Transactional
    public ResponseEntity<MessageDTO> create(@RequestBody @Valid GroupDataInput data) {
        GroupDataComplete permission = service.create(data);
        MessageDTO messageDTO = messageService.createMessage(201, permission);

        return ResponseEntity.ok(messageDTO);
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity delete(@PathVariable String id) {
        service.delete(Integer.parseInt(id));

        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<MessageDTO> update(@PathVariable String id, @RequestBody @Valid GroupDataEdit data) {
        GroupDataComplete groupDto = service.update(data, Integer.parseInt(id));
        MessageDTO messageDTO = messageService.createMessage(200, groupDto);

        return ResponseEntity.ok(messageDTO);
    }

    @GetMapping
    public ResponseEntity getAll() {
        List<GroupDataComplete> listPermissions = service.getAll();

        MessageDTO messageDTO = messageService.createMessage(200, listPermissions);

        return ResponseEntity.ok(messageDTO);
    }

    @GetMapping("/{id}")
    public ResponseEntity getById(@PathVariable String id) {
        GroupDataComplete permission = service.getById(Integer.parseInt(id));

        MessageDTO messageDTO = messageService.createMessage(200, permission);

        return ResponseEntity.ok(messageDTO);
    }
}
