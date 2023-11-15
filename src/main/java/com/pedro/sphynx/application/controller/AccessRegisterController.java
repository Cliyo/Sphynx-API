package com.pedro.sphynx.application.controller;

import com.pedro.sphynx.application.dtos.access.AccessDataComplete;
import com.pedro.sphynx.application.dtos.access.AccessDataInput;
import com.pedro.sphynx.application.dtos.person.PersonDataComplete;
import com.pedro.sphynx.domain.AccessService;
import com.pedro.sphynx.infrastructure.repository.AccessRepository;
import com.pedro.sphynx.infrastructure.repository.PersonRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("accessRegister")
public class AccessRegisterController {

    @Autowired
    private AccessRepository accessRepository;

    @Autowired
    private AccessService accessService;

    @Autowired
    private PersonRepository personRepository;

    @PostMapping
    @Transactional
    public ResponseEntity create(@RequestBody @Valid AccessDataInput data){
        accessService.validateCreation(data);

        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<List<AccessDataComplete>> getAll(){
        List<AccessDataComplete> finalList = new ArrayList<>();
        var listAccess = accessRepository.findAll().stream().map(AccessDataComplete::new).toList();

        for(AccessDataComplete dto : listAccess){
            var person = new PersonDataComplete(personRepository.findOneByRa(dto.ra()));
            var finalDto = dto.withName(dto, person.name());

            finalList.add(finalDto);
        }

        return ResponseEntity.ok(finalList);
    }

    @GetMapping("/byRa/{ra}")
    public ResponseEntity<List<AccessDataComplete>> getAllByRa(@PathVariable String ra){
        List<AccessDataComplete> finalList = new ArrayList<>();
        var listAccess = accessRepository.findAllByRa(ra).stream().map(AccessDataComplete::new).toList();

        for(AccessDataComplete dto : listAccess){
            var person = new PersonDataComplete(personRepository.findOneByRa(dto.ra()));
            var finalDto = dto.withName(dto, person.name());

            finalList.add(finalDto);
        }

        return ResponseEntity.ok(finalList);
    }

    @GetMapping("/byLocal/{local}")
    public ResponseEntity<List<AccessDataComplete>> getAllByLocal(@PathVariable String local){
        String localRep = local.replace("_", " ");

        List<AccessDataComplete> finalList = new ArrayList<>();
        var listAccess = accessRepository.findAllByLocal(localRep).stream().map(AccessDataComplete::new).toList();

        for(AccessDataComplete dto : listAccess){
            var person = new PersonDataComplete(personRepository.findOneByRa(dto.ra()));
            var finalDto = dto.withName(dto, person.name());

            finalList.add(finalDto);
        }

        return ResponseEntity.ok(finalList);
    }

    @GetMapping("/byDate/{date}")
    public ResponseEntity<List<AccessDataComplete>> getAllByDate(@PathVariable String date){

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDateTime dateTimeStart = LocalDate.parse(date, formatter).atStartOfDay();
        LocalDateTime dateTimeEnd = dateTimeStart.plusDays(1);

        List<AccessDataComplete> finalList = new ArrayList<>();
        var listAccess = accessRepository.findAllByDateBetween(dateTimeStart, dateTimeEnd).stream().map(AccessDataComplete::new).toList();

        for(AccessDataComplete dto : listAccess){
            var person = new PersonDataComplete(personRepository.findOneByRa(dto.ra()));
            var finalDto = dto.withName(dto, person.name());

            finalList.add(finalDto);
        }

        return ResponseEntity.ok(finalList);
    }
}
