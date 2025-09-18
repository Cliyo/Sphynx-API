package com.pedro.sphynx.controllers;

import com.pedro.sphynx.dtos.local.LocalDataEditInput;
import com.pedro.sphynx.dtos.local.LocalDataInput;
import com.pedro.sphynx.dtos.localGroup.LocalGroupDataComplete;
import com.pedro.sphynx.dtos.message.MessageDTO;
import com.pedro.sphynx.entities.User;
import com.pedro.sphynx.services.LocalService;
import com.pedro.sphynx.utils.CreateMessageUtil;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("locals")
public class LocalController{

    @Autowired
    private LocalService service;

    @Autowired
    private CreateMessageUtil createMessageUtil;

    @PostMapping
    @Transactional
    public ResponseEntity<MessageDTO> create(@RequestBody @Valid LocalDataInput data, @AuthenticationPrincipal UserDetails user){
        var local = service.create(data, (User) user);
        MessageDTO messageDTO = createMessageUtil.createMessage(201, local);

        return ResponseEntity.ok(messageDTO);
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<MessageDTO> update(@PathVariable String id, @RequestBody @Valid LocalDataEditInput data){
        var local = service.update(data, Integer.parseInt(id));
        MessageDTO messageDTO = createMessageUtil.createMessage(200, local);

        return ResponseEntity.ok(messageDTO);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MessageDTO> getById(@PathVariable String id){
        var local = service.getById(Long.parseLong(id));
        MessageDTO messageDTO = createMessageUtil.createMessage(200, local);

        return ResponseEntity.ok(messageDTO);
    }

    @GetMapping
    public ResponseEntity<MessageDTO> getAll(){
        List<LocalGroupDataComplete> localsList = service.getAllLocalsWithGroups();
        MessageDTO messageDTO = createMessageUtil.createMessage(200, localsList);

        return ResponseEntity.ok(messageDTO);
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<Void> delete(@PathVariable String id){
        service.deleteById(Long.parseLong(id));

        return ResponseEntity.noContent().build();
    }
}
