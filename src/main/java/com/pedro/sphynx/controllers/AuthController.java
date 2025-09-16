package com.pedro.sphynx.controllers;

import com.pedro.sphynx.dtos.auth.UserDataLoginInput;
import com.pedro.sphynx.dtos.auth.UserDataOutputLogin;
import com.pedro.sphynx.dtos.auth.UserDataRegisterInput;
import com.pedro.sphynx.dtos.auth.UserDataVerifyInput;
import com.pedro.sphynx.dtos.auth.UserDataVerifyOutput;
import com.pedro.sphynx.services.TokenService;
import com.pedro.sphynx.entities.User;
import com.pedro.sphynx.repositories.UserRepository;
import jakarta.validation.Valid;
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
    private UserRepository userRepository;

    @Autowired
    private CreateMessageUtil createMessageUtil;

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody @Valid UserDataLoginInput data){
        var token = new UsernamePasswordAuthenticationToken(data.user(), data.password());
        var auth = manager.authenticate(token);
        var jwtToken = tokenService.generateToken((User) auth.getPrincipal());

        return ResponseEntity.ok(new UserDataOutputLogin(jwtToken));
    }

    @PostMapping("/verify")
    public ResponseEntity verify(@RequestBody @Valid UserDataVerifyInput data){
        String subject = tokenService.getSubject(data.token());

        if(userRepository.existsByUser(subject)){
            return ResponseEntity.ok(new UserDataVerifyOutput(data.token(), true));
        } else{
            return ResponseEntity.ok(new UserDataVerifyOutput(data.token(), false));
        }
    }

    @PostMapping("/register")
    public ResponseEntity<MessageDTO> register(@RequestBody @Valid UserDataRegisterInput data) {
        if (userRepository.existsByUser(data.user())) {
            return ResponseEntity.badRequest().body(new MessageDTO(404, "User already exists", null));
        }

        if (userRepository.existsByRa(data.ra())) {
            return ResponseEntity.badRequest().body(new MessageDTO(404, "RA already exists", null));
        }

        var user = new User(null, data.name(), data.ra(), data.isAdmin(), data.user(), data.password());
        userRepository.save(user);

        MessageDTO messageDto = createMessageUtil.createMessage(201, user);

        return ResponseEntity.ok().body(messageDto);
    }
}
