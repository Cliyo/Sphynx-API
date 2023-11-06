package com.pedro.sphynx.application.controller;

import com.pedro.sphynx.application.dtos.auth.UserDataLoginInputDTO;
import com.pedro.sphynx.application.dtos.auth.UserDataOutputLoginDTO;
import com.pedro.sphynx.application.dtos.auth.UserDataVerifyInputDTO;
import com.pedro.sphynx.application.dtos.auth.UserDataVerifyOutputDTO;
import com.pedro.sphynx.domain.TokenService;
import com.pedro.sphynx.infrastructure.entities.User;
import com.pedro.sphynx.infrastructure.repository.UserRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("login")
public class AuthController {
    @Autowired
    private AuthenticationManager manager;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private UserRepository userRepository;

    @PostMapping
    public ResponseEntity login(@RequestBody @Valid UserDataLoginInputDTO data){
        var token = new UsernamePasswordAuthenticationToken(data.user(), data.password());
        var auth = manager.authenticate(token);
        var jwtToken = tokenService.generateToken((User) auth.getPrincipal());

        return ResponseEntity.ok(new UserDataOutputLoginDTO(jwtToken));
    }

    @PostMapping("/verify")
    public ResponseEntity verify(@RequestBody @Valid UserDataVerifyInputDTO data){
        String subject = tokenService.getSubject(data.token());

        if(userRepository.existsByUser(subject)){
            return ResponseEntity.ok(new UserDataVerifyOutputDTO(data.token(), true));
        } else{
            return ResponseEntity.ok(new UserDataVerifyOutputDTO(data.token(), false));
        }
    }
}
