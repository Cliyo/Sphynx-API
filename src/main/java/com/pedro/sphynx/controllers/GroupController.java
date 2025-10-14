package com.pedro.sphynx.controllers;

import com.pedro.sphynx.dtos.group.GroupDataEdit;
import com.pedro.sphynx.dtos.message.MessageDTO;
import com.pedro.sphynx.entities.User;
import com.pedro.sphynx.dtos.group.GroupDataComplete;
import com.pedro.sphynx.dtos.group.GroupDataInput;
import com.pedro.sphynx.utils.CreateMessageUtil;
import com.pedro.sphynx.services.GroupService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("groups")
public class GroupController {

    @Autowired
    private GroupService service;

    @Autowired
    private CreateMessageUtil createMessageUtil;

    @PostMapping
    @Transactional
    public ResponseEntity<MessageDTO> create(@RequestBody @Valid GroupDataInput data, @AuthenticationPrincipal UserDetails user) {
        GroupDataComplete permission = service.create(data, (User) user);
        MessageDTO messageDTO = createMessageUtil.createMessage(201, permission);

        return ResponseEntity.ok(messageDTO);
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<Void> delete(@PathVariable String id, @AuthenticationPrincipal UserDetails user) {
        service.delete(Integer.parseInt(id), (User) user);

        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<MessageDTO> update(
        @PathVariable String id, 
        @RequestBody @Valid GroupDataEdit data, 
        @AuthenticationPrincipal UserDetails user
    ) {
        GroupDataComplete groupDto = service.update(data, Integer.parseInt(id), (User) user);
        MessageDTO messageDTO = createMessageUtil.createMessage(200, groupDto);

        return ResponseEntity.ok(messageDTO);
    }

    @GetMapping
    public ResponseEntity<MessageDTO> getAll(@RequestParam Optional<String> name, @AuthenticationPrincipal UserDetails user) {
        List<GroupDataComplete> listPermissions = name.isPresent() ? service.getAllByName(name.get(), (User) user) : service.getAll((User) user);

        MessageDTO messageDTO = createMessageUtil.createMessage(200, listPermissions);

        return ResponseEntity.ok(messageDTO);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MessageDTO> getById(@PathVariable String id, @AuthenticationPrincipal UserDetails user) {
        GroupDataComplete permission = service.getById(Integer.parseInt(id), (User) user);

        MessageDTO messageDTO = createMessageUtil.createMessage(200, permission);

        return ResponseEntity.ok(messageDTO);
    }
}
