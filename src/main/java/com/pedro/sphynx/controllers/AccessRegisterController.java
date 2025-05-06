package com.pedro.sphynx.controllers;

import com.pedro.sphynx.dtos.access.AccessDataComplete;
import com.pedro.sphynx.dtos.access.AccessDataFingerprintInput;
import com.pedro.sphynx.dtos.access.AccessDataTagInput;
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

    @PostMapping("/tag")
    @Transactional
    public ResponseEntity<MessageDTO> create(@RequestBody @Valid AccessDataTagInput data){
        System.out.println(data);
        AccessDataComplete accessDataComplete = service.validateCreation(data);

        MessageDTO dto = createMessageUtil.createMessage(201, accessDataComplete);

        return ResponseEntity.ok(dto);
    }

    @PostMapping("/fingerprint")
    @Transactional
    public ResponseEntity<MessageDTO> create(@RequestBody @Valid AccessDataFingerprintInput data){
        System.out.println(data);
        AccessDataComplete accessDataComplete = service.validateCreation(data);

        MessageDTO dto = createMessageUtil.createMessage(201, accessDataComplete);

        return ResponseEntity.ok(dto);
    }

    @GetMapping
    public ResponseEntity<MessageDTO> getAll(@RequestParam("ra") Optional<String> ra, @RequestParam("local") Optional<String> local, @RequestParam("date") Optional<String> date){
        List<AccessDataComplete> listAccess = service.getAllAccesses(ra, local, date);
        MessageDTO messageDTO = createMessageUtil.createMessage(200, listAccess);

        return ResponseEntity.ok(messageDTO);
    }
}
