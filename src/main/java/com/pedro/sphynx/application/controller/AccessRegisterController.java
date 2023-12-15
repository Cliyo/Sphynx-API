package com.pedro.sphynx.application.controller;

import com.pedro.sphynx.application.dtos.access.AccessDataComplete;
import com.pedro.sphynx.application.dtos.access.AccessDataInput;
import com.pedro.sphynx.domain.AccessService;
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

@RestController
@RequestMapping("accessRegister")
public class AccessRegisterController {

    @Autowired
    private AccessRepository repository;

    @Autowired
    private AccessService service;

    @PostMapping
    @Transactional
    public ResponseEntity create(@RequestBody @Valid AccessDataInput data){
        AccessDataComplete accessDataComplete = service.validateCreation(data);

        return ResponseEntity.ok(accessDataComplete);
    }

    @GetMapping
    public ResponseEntity<List<AccessDataComplete>> getAll(){
        var listAccess = repository.findAll().stream().map(AccessDataComplete::new).toList();

        return ResponseEntity.ok(listAccess);
    }

    @GetMapping("/byRa/{ra}")
    public ResponseEntity<List<AccessDataComplete>> getAllByRa(@PathVariable String ra){
        var listAccess = repository.findAllByConsumerPersonRa(ra).stream().map(AccessDataComplete::new).toList();

        return ResponseEntity.ok(listAccess);
    }

    @GetMapping("/byLocal/{local}")
    public ResponseEntity<List<AccessDataComplete>> getAllByLocal(@PathVariable String local){
        String localRep = local.replace("_", " ");

        var listAccess = repository.findAllByLocalName(localRep).stream().map(AccessDataComplete::new).toList();

        return ResponseEntity.ok(listAccess);
    }

    @GetMapping("/byDate/{date}")
    public ResponseEntity<List<AccessDataComplete>> getAllByDate(@PathVariable String date){

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDateTime dateTimeStart = LocalDate.parse(date, formatter).atStartOfDay();
        LocalDateTime dateTimeEnd = dateTimeStart.plusDays(1);

        var listAccess = repository.findAllByDateBetween(dateTimeStart, dateTimeEnd).stream().map(AccessDataComplete::new).toList();

        return ResponseEntity.ok(listAccess);
    }
}
