package com.pedro.sphynx.controllers;

import com.pedro.sphynx.dtos.auth.UserDataComplete;
import com.pedro.sphynx.dtos.auth.UserDataLoginInput;
import com.pedro.sphynx.dtos.auth.UserDataOutputLogin;
import com.pedro.sphynx.dtos.auth.UserDataRegisterInput;
import com.pedro.sphynx.dtos.auth.UserDataVerifyInput;
import com.pedro.sphynx.dtos.auth.UserDataVerifyOutput;
import com.pedro.sphynx.services.AuthService;
import com.pedro.sphynx.services.TokenService;
import com.pedro.sphynx.services.UserService;
import com.pedro.sphynx.entities.User;
import jakarta.validation.Valid;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;
import com.pedro.sphynx.dtos.message.MessageDTO;
import com.pedro.sphynx.utils.CreateMessageUtil;


@RestController
@RequestMapping("auth")
public class AuthController {
    @Autowired
    private AuthenticationManager manager;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private UserService userService;

    @Autowired
    private AuthService authService;

    @Autowired
    private CreateMessageUtil createMessageUtil;

    @PostMapping("/login")
    public ResponseEntity<UserDataOutputLogin> login(@RequestBody @Valid UserDataLoginInput data){
        var token = new UsernamePasswordAuthenticationToken(data.user(), data.password());
        var auth = manager.authenticate(token);
        
        User authenticatedUser = (User) auth.getPrincipal();
        var jwtToken = tokenService.generateToken(authenticatedUser);

        return ResponseEntity.ok(new UserDataOutputLogin(jwtToken));
    }

    @PostMapping("/verify")
    public ResponseEntity<UserDataVerifyOutput> verify(@RequestBody @Valid UserDataVerifyInput data){
        return ResponseEntity.ok(authService.verifyToken(data));
    }

    @PostMapping("/register")
    public ResponseEntity<MessageDTO> register(@RequestBody @Valid UserDataRegisterInput data) {
        UserDataRegisterInput processedData = 
            data.isAdmin() != null ? data : new UserDataRegisterInput(data.user(), data.name(), data.ra(), false);

        UserDataComplete user = userService.create(processedData);
        MessageDTO messageDto = createMessageUtil.createMessage(201, user);
        return ResponseEntity.ok().body(messageDto);
    }

    @GetMapping("/users")
    public ResponseEntity<MessageDTO> getAll() {
        List<UserDataComplete> users = userService.getAll();
        MessageDTO messageDto = createMessageUtil.createMessage(200, users);

        return ResponseEntity.ok().body(messageDto);
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<MessageDTO> getById(@PathVariable Long id) {
        UserDataComplete user = userService.getById(id);
        MessageDTO messageDto = createMessageUtil.createMessage(200, user);

        return ResponseEntity.ok().body(messageDto);
    }
    

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        userService.delete(id);

        return ResponseEntity.noContent().build();
    }
}
