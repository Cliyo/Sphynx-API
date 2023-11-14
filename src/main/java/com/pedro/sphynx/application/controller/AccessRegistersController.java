package com.pedro.sphynx.application.controller;

import com.pedro.sphynx.application.dtos.access.AccessDataComplete;
import com.pedro.sphynx.application.dtos.access.AccessDataInput;
import com.pedro.sphynx.application.dtos.consumer.ConsumerDataComplete;
import com.pedro.sphynx.application.dtos.person.PersonDataComplete;
import com.pedro.sphynx.domain.AccessService;
import com.pedro.sphynx.infrastructure.repository.AccessRepository;
import jakarta.validation.Valid;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("accessRegister")
public class AccessRegistersController {

    @Autowired
    private AccessRepository accessRepository;

    @Autowired
    private AccessService accessService;

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

        System.out.println(accessRepository.findAll().stream().map(AccessDataComplete::new).toList());

        for(AccessDataComplete dto : listAccess){
            var person = new AccessDataComplete(accessRepository.findOneByRa(dto.ra()));
            var finalDto = dto.withName(dto, person.name());

            finalList.add(finalDto);
        }
        return ResponseEntity.ok(finalList);
    }
}
