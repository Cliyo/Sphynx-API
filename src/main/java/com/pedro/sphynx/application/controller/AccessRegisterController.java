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
    private AccessRepository repository;

    @Autowired
    private AccessService service;

    @Autowired
    private MessageService messageService;

    @PostMapping
    @Transactional
    public ResponseEntity create(@RequestBody @Valid AccessDataInput data){
        AccessDataComplete accessDataComplete = service.validateCreation(data);

        MessageDTO dto = messageService.createMessage(201, accessDataComplete, null);

        return ResponseEntity.ok(dto);
    }

    @GetMapping
    public ResponseEntity<List<AccessDataComplete>> getAll(@RequestParam("ra") Optional<String> ra, @RequestParam("local") Optional<String> local, @RequestParam("date") Optional<String> date){
        List<AccessDataComplete> listAccess;

        if(ra.isPresent() && local.isEmpty() && date.isEmpty()){
            listAccess = repository.findAllByConsumerRa(ra.get()).stream().map(AccessDataComplete::new).toList();
        }

        else if(ra.isEmpty() && local.isPresent() && date.isEmpty()){
            listAccess = repository.findAllByLocalName(local.get()).stream().map(AccessDataComplete::new).toList();
        }

        else if(ra.isEmpty() && local.isEmpty() && date.isPresent()){
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDateTime dateTimeStart = LocalDate.parse(date.get(), formatter).atStartOfDay();
            LocalDateTime dateTimeEnd = dateTimeStart.plusDays(1);

            listAccess = repository.findAllByDateBetween(dateTimeStart, dateTimeEnd).stream().map(AccessDataComplete::new).toList();
        }

        else if(ra.isPresent() &&  local.isPresent() && date.isPresent()){
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDateTime dateTimeStart = LocalDate.parse(date.get(), formatter).atStartOfDay();
            LocalDateTime dateTimeEnd = dateTimeStart.plusDays(1);

            listAccess = repository.findAllByConsumer_RaAndLocal_NameAndDateBetween(ra.get(), local.get(), dateTimeStart, dateTimeEnd).stream().map(AccessDataComplete::new).toList();
        }

        else{
            listAccess = repository.findAll().stream().map(AccessDataComplete::new).toList();
        }

        return ResponseEntity.ok(listAccess);
    }
}
