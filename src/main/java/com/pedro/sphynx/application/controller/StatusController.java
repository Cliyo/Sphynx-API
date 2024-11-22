package com.pedro.sphynx.application.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pedro.sphynx.application.dtos.message.MessageDTO;
import com.pedro.sphynx.domain.MessageService;

@RestController
@RequestMapping("online")
public class StatusController {

    @Autowired
    private MessageService messageService;

    @GetMapping
    public ResponseEntity online() {
        Map<String, String> objeto = new HashMap<>();
        objeto.put("message", "Testando conectividade");
        MessageDTO dto = messageService.createMessage(200, objeto, null);
        return ResponseEntity.ok(dto);
    }
}
