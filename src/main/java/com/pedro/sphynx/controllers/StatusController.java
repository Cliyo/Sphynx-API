package com.pedro.sphynx.controllers;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pedro.sphynx.dtos.message.MessageDTO;
import com.pedro.sphynx.utils.CreateMessageUtil;

@RestController
@RequestMapping("online")
public class StatusController {

    @Autowired
    private CreateMessageUtil createMessageUtil;

    @GetMapping
    public ResponseEntity<MessageDTO> online() {
        Map<String, String> objeto = new HashMap<>();
        objeto.put("message", "Testando conectividade");
        MessageDTO dto = createMessageUtil.createMessage(200, objeto);
        return ResponseEntity.ok(dto);
    }
}
