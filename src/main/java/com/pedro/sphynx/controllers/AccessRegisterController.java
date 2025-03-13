package com.pedro.sphynx.controllers;

import com.pedro.sphynx.dtos.access.AccessDataComplete;
import com.pedro.sphynx.dtos.access.AccessDataInput;
import com.pedro.sphynx.dtos.message.MessageDTO;
import com.pedro.sphynx.services.AccessService;
import com.pedro.sphynx.utils.CreateMessageUtil;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("accessRegisters")
public class AccessRegisterController {

    @Autowired
    private AccessService service;

    @Autowired
    private CreateMessageUtil createMessageUtil;

    @PostMapping
    @Transactional
    public ResponseEntity create(@RequestBody @Valid AccessDataInput data){
        AccessDataComplete accessDataComplete = service.validateCreation(data);

        MessageDTO messageDTO = createMessageUtil.createMessage(201, accessDataComplete);

        return ResponseEntity.ok(messageDTO);
    }

    @GetMapping
    public ResponseEntity<MessageDTO> getAll(@RequestParam("ra") Optional<String> ra, @RequestParam("local") Optional<String> local, @RequestParam("date") Optional<String> date){
        List<AccessDataComplete> listAccess = service.getAllAccesses(ra, local, date);
        MessageDTO messageDTO = createMessageUtil.createMessage(200, listAccess);

        return ResponseEntity.ok(messageDTO);
    }
}
