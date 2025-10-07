package com.pedro.sphynx.controllers;

import com.pedro.sphynx.dtos.auth.UserDataComplete;
import com.pedro.sphynx.dtos.auth.UserDataRegisterInput;
import com.pedro.sphynx.dtos.message.MessageDTO;
import com.pedro.sphynx.entities.User;
import com.pedro.sphynx.services.UserService;
import com.pedro.sphynx.utils.CreateMessageUtil;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("users")
public class UserController{

    @Autowired
    private UserService service;

    @Autowired
    private CreateMessageUtil createMessageUtil;

    @PostMapping("/register")
    public ResponseEntity<MessageDTO> register(@RequestBody @Valid UserDataRegisterInput data, @AuthenticationPrincipal UserDetails user) {
        UserDataRegisterInput processedData = 
            data.isAdmin() != null ? data : new UserDataRegisterInput(data.user(), data.name(), data.ra(), data.group(), data.permissionMenu(), data.tag(), false);

        UserDataComplete userCreated = service.create(processedData, (User) user);
        MessageDTO messageDto = createMessageUtil.createMessage(201, userCreated);
        return ResponseEntity.ok().body(messageDto);
    }

    @GetMapping("/")
    public ResponseEntity<MessageDTO> getAll(@AuthenticationPrincipal UserDetails user) {
        List<UserDataComplete> users = service.getAll((User) user);
        MessageDTO messageDto = createMessageUtil.createMessage(200, users);

        return ResponseEntity.ok().body(messageDto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MessageDTO> getById(@PathVariable Long id) {
        UserDataComplete user = service.getById(id);
        MessageDTO messageDto = createMessageUtil.createMessage(200, user);

        return ResponseEntity.ok().body(messageDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);

        return ResponseEntity.noContent().build();
    }
}
