package com.pedro.sphynx.application.controller;

import com.pedro.sphynx.application.dtos.consumer.ConsumerDataComplete;
import com.pedro.sphynx.application.dtos.message.MessageDTO;
import com.pedro.sphynx.application.dtos.permission.PermissionDataComplete;
import com.pedro.sphynx.application.dtos.permission.PermissionDataInput;
import com.pedro.sphynx.domain.MessageService;
import com.pedro.sphynx.domain.PermissionService;
import com.pedro.sphynx.infrastructure.repository.PermissionRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("permissions")
public class PermissionController implements ControllerIN<PermissionDataInput, PermissionDataComplete> {

    @Autowired
    private PermissionRepository repository;

    @Autowired
    private PermissionService service;

    @Autowired
    private MessageService messageService;

    @Override
    @PostMapping
    @Transactional
    public ResponseEntity create(@RequestBody @Valid PermissionDataInput data, @RequestHeader("Language") String language) {
        var permission = service.createVerify(data, language);
        MessageDTO dto = messageService.createMessage(201, permission, language);

        return ResponseEntity.ok(dto);
    }

    @Override
    public ResponseEntity update(String id, PermissionDataComplete data, String language) {
        return null;
    }

    @Override
    @DeleteMapping("/{level}")
    @Transactional
    public ResponseEntity delete(@PathVariable String level) {
        repository.deleteByLevel(Integer.parseInt(level));

        return ResponseEntity.noContent().build();
    }

    @Override
    @GetMapping
    public ResponseEntity get() {
        var listPermissions = repository.findAll().stream().map(PermissionDataComplete::new).toList();

        return ResponseEntity.ok(listPermissions);
    }
}
