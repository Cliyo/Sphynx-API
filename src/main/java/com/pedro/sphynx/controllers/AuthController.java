package com.pedro.sphynx.controllers;

import com.pedro.sphynx.dtos.auth.UserDataLoginInput;
import com.pedro.sphynx.dtos.auth.UserDataOutputLogin;
import com.pedro.sphynx.dtos.auth.UserDataVerifyInput;
import com.pedro.sphynx.dtos.auth.UserDataVerifyOutput;
import com.pedro.sphynx.dtos.auth.UserRecoveryPasswordEmail;
import com.pedro.sphynx.dtos.auth.UserResetPassword;
import com.pedro.sphynx.services.AuthService;
import com.pedro.sphynx.services.TokenService;
import com.pedro.sphynx.entities.User;
import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("auth")
public class AuthController {
    @Autowired
    private AuthenticationManager manager;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private AuthService authService;

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

    @PostMapping("/password-recovery")
    public ResponseEntity<Void> passwordRecovery(@RequestBody UserRecoveryPasswordEmail user) {

        authService.passwordRecovery(user.user());

        return ResponseEntity.ok().build();
    }

    @PostMapping("/password-recovery/{id}/{hash}")
    public ResponseEntity<Void> passwordReset(@PathVariable Long id, @PathVariable String hash, @RequestBody UserResetPassword user) {

        authService.passwordReset(id, hash, user.newPassword());

        return ResponseEntity.ok().build();
    }
    
}
