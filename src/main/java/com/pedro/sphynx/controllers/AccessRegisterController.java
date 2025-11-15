package com.pedro.sphynx.controllers;

import com.pedro.sphynx.dtos.access.AccessDataComplete;
import com.pedro.sphynx.dtos.access.AccessDataFingerprintInput;
import com.pedro.sphynx.dtos.access.AccessDataTagInput;
import com.pedro.sphynx.dtos.message.MessageDTO;
import com.pedro.sphynx.entities.User;
import com.pedro.sphynx.services.AccessService;
import com.pedro.sphynx.utils.CreateMessageUtil;
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
@RequestMapping("accessRegisters")
public class AccessRegisterController {

    @Autowired
    private AccessService service;

    @Autowired
    private CreateMessageUtil createMessageUtil;

    @PostMapping("/tag")
    @Transactional
    public ResponseEntity<MessageDTO> create(@RequestBody @Valid AccessDataTagInput data){
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
    public ResponseEntity<MessageDTO> getAll(@AuthenticationPrincipal UserDetails user, @RequestParam Optional<Long> unitId){
        List<AccessDataComplete> listAccess = service.getAllAccesses((User) user, unitId);
        MessageDTO messageDTO = createMessageUtil.createMessage(200, listAccess);

        return ResponseEntity.ok(messageDTO);
    }
}
