package com.pedro.sphynx.controllers;

import com.pedro.sphynx.dtos.auth.UserDataComplete;
import com.pedro.sphynx.dtos.auth.UserDataLoginInput;
import com.pedro.sphynx.dtos.auth.UserDataOutputLogin;
import com.pedro.sphynx.dtos.auth.UserDataRegisterInput;
import com.pedro.sphynx.dtos.auth.UserDataVerifyInput;
import com.pedro.sphynx.dtos.auth.UserDataVerifyOutput;
import com.pedro.sphynx.services.TokenService;
import com.pedro.sphynx.services.UserService;
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
    private UserService userService;

    @Autowired
    private CreateMessageUtil createMessageUtil;

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody @Valid UserDataLoginInput data){
        var token = new UsernamePasswordAuthenticationToken(data.user(), data.password());
        var auth = manager.authenticate(token);
        
        User authenticatedUser = (User) auth.getPrincipal();
        var jwtToken = tokenService.generateToken(authenticatedUser);

        return ResponseEntity.ok(new UserDataOutputLogin(jwtToken, authenticatedUser.isAdmin()));
    }

    @PostMapping("/verify")
    public ResponseEntity verify(@RequestBody @Valid UserDataVerifyInput data){
        String subject = tokenService.getSubject(data.token());

        if(userRepository.existsByUser(subject)){

            User user = (User) userRepository.findByUser(subject);

            return ResponseEntity.ok(new UserDataVerifyOutput(data.token(), user.isAdmin(), true));
        } else{
            return ResponseEntity.ok(new UserDataVerifyOutput(data.token(), false, false));
        }
    }

    @PostMapping("/register")
    public ResponseEntity<MessageDTO> register(@RequestBody @Valid UserDataRegisterInput data) {
        UserDataComplete user = userService.create(data);
        MessageDTO messageDto = createMessageUtil.createMessage(201, user);
        return ResponseEntity.ok().body(messageDto);
    }
}
