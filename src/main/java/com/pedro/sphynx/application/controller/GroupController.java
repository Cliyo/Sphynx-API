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

@RestController
@RequestMapping("groups")
public class GroupController {

    @Autowired
    private GroupService service;

    @Autowired
    private MessageService messageService;

    @PostMapping
    @Transactional
    public ResponseEntity create(@RequestBody @Valid GroupDataInput data) {
        var permission = service.create(data);
        MessageDTO dto = messageService.createMessage(201, permission);

        return ResponseEntity.ok(dto);
    }

    public ResponseEntity update(String id, GroupDataComplete data) {
        return null;
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity delete(@PathVariable String id) {
        service.delete(Integer.parseInt(id));

        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity update(@PathVariable String id, @RequestBody @Valid GroupDataEdit data) {
        var groupDto = service.update(data, Long.parseLong(id));
        MessageDTO dto = messageService.createMessage(200, groupDto);

        return ResponseEntity.ok(dto);
    }

    @GetMapping
    public ResponseEntity getAll() {
        var listPermissions = service.getAll();

        return ResponseEntity.ok(listPermissions);
    }

    @GetMapping("/{id}")
    public ResponseEntity getById(@PathVariable String id) {
        var permission = service.getById(Integer.parseInt(id));

        return ResponseEntity.ok(permission);
    }
}
