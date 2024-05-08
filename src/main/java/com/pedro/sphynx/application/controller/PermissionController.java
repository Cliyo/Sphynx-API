package com.pedro.sphynx.application.controller;

import com.pedro.sphynx.application.dtos.consumer.ConsumerDataComplete;
import com.pedro.sphynx.application.dtos.message.MessageDTO;
import com.pedro.sphynx.application.dtos.permission.PermissionDataComplete;
import com.pedro.sphynx.application.dtos.permission.PermissionDataInput;
import com.pedro.sphynx.domain.MessageService;
import com.pedro.sphynx.domain.PermissionService;
import com.pedro.sphynx.infrastructure.repository.PermissionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public ResponseEntity create(PermissionDataInput data, String language) {
        var local = service.createVerify(data, language);
        MessageDTO dto = messageService.createMessage(201, local, language);

        return ResponseEntity.ok(dto);
    }

    @Override
    public ResponseEntity update(String id, PermissionDataComplete data, String language) {
        return null;
    }

    @Override
    public ResponseEntity delete(String name) {
        repository.deleteByName(name);

        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity get() {
        var listPermissions = repository.findAll().stream().map(PermissionDataComplete::new).toList();

        return ResponseEntity.ok(listPermissions);
    }
}
