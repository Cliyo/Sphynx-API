package com.pedro.sphynx.controllers;

import com.pedro.sphynx.dtos.message.MessageDTO;
import com.pedro.sphynx.dtos.unit.UnitDataComplete;
import com.pedro.sphynx.dtos.unit.UnitDataEdit;
import com.pedro.sphynx.dtos.unit.UnitDataInput;
import com.pedro.sphynx.entities.User;
import com.pedro.sphynx.utils.CreateMessageUtil;
import com.pedro.sphynx.services.UnitService;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("units")
public class UnitController {

    @Autowired
    private UnitService service;

    @Autowired
    private CreateMessageUtil createMessageUtil;

    @PostMapping
    @Transactional
    public ResponseEntity<MessageDTO> create(@RequestBody @Valid UnitDataInput data, @AuthenticationPrincipal UserDetails user) {
        UnitDataComplete unit = service.create(data, (User) user);
        MessageDTO messageDTO = createMessageUtil.createMessage(201, unit);

        return ResponseEntity.ok(messageDTO);
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<Void> delete(@PathVariable String id, @AuthenticationPrincipal UserDetails user) {
        service.delete(Long.parseLong(id), (User) user);

        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<MessageDTO> update(@PathVariable String id, @RequestBody @Valid UnitDataEdit data, @AuthenticationPrincipal UserDetails user) {
        UnitDataComplete unitDto = service.update(data, Long.parseLong(id), (User) user);
        MessageDTO messageDTO = createMessageUtil.createMessage(200, unitDto);

        return ResponseEntity.ok(messageDTO);
    }

    @GetMapping
    public ResponseEntity<MessageDTO> getAll(@AuthenticationPrincipal UserDetails user) {
        List<UnitDataComplete> listUnits = service.getAll((User) user);

        MessageDTO messageDTO = createMessageUtil.createMessage(200, listUnits);

        return ResponseEntity.ok(messageDTO);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MessageDTO> getById(@PathVariable String id, @AuthenticationPrincipal UserDetails user) {
        UnitDataComplete unit = service.getById(Long.parseLong(id), (User) user);

        MessageDTO messageDTO = createMessageUtil.createMessage(200, unit);

        return ResponseEntity.ok(messageDTO);
    }
}
