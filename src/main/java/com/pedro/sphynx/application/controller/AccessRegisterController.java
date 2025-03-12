package com.pedro.sphynx.application.controller;

import com.pedro.sphynx.application.dtos.access.AccessDataComplete;
import com.pedro.sphynx.application.dtos.access.AccessDataInput;
import com.pedro.sphynx.application.dtos.message.MessageDTO;
import com.pedro.sphynx.domain.AccessService;
import com.pedro.sphynx.domain.MessageService;
import com.pedro.sphynx.infrastructure.repository.AccessRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("accessRegisters")
public class AccessRegisterController {

    @Autowired
    private AccessService service;

    @Autowired
    private MessageService messageService;

    @PostMapping
    @Transactional
    public ResponseEntity create(@RequestBody @Valid AccessDataInput data){
        AccessDataComplete accessDataComplete = service.validateCreation(data);

        MessageDTO dto = messageService.createMessage(201, accessDataComplete);

        return ResponseEntity.ok(dto);
    }

    @GetMapping
    public ResponseEntity<List<AccessDataComplete>> getAll(@RequestParam("ra") Optional<String> ra, @RequestParam("local") Optional<String> local, @RequestParam("date") Optional<String> date){
        List<AccessDataComplete> listAccess = service.getAllAccesses(ra, local, date);

        return ResponseEntity.ok(listAccess);
    }
}
