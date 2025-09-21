package com.pedro.sphynx.services;

import com.pedro.sphynx.dtos.auth.UserDataVerifyInput;
import com.pedro.sphynx.dtos.auth.UserDataVerifyOutput;
import com.pedro.sphynx.entities.RecoveryHash;
import com.pedro.sphynx.entities.User;
import com.pedro.sphynx.repositories.RecoveryHashRepository;
import com.pedro.sphynx.repositories.UserRepository;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService implements UserDetailsService {

    @Autowired
    private UserRepository repository;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private EmailService emailService;

    @Autowired
    private RecoveryHashRepository recoveryHashRepository;

    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder(12);

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return repository.findByUser(username);
    }

    public UserDataVerifyOutput verifyToken(UserDataVerifyInput data) {
        String subject = tokenService.getSubject(data.token());

        if(repository.existsByUser(subject)){

            User user = (User) repository.findByUser(subject);

            return new UserDataVerifyOutput(data.token(), user.isAdmin(), true);
        } else{
            return new UserDataVerifyOutput(data.token(), false, false);
        }
    }

    public void passwordRecovery(String user) {
        User existingUser = (User) repository.findByUser(user);
        if (existingUser == null) {
            throw new UsernameNotFoundException("User not found");
        }

        String randomHash = Long.toHexString(Double.doubleToLongBits(Math.random()));

        recoveryHashRepository.save(new RecoveryHash(null, existingUser, randomHash, true, LocalDateTime.now(), null));

        emailService.sendSimpleMessage(
            existingUser.getUser(),
            "Sphynx | Recuperar senha",
            "Clique no link para recuperar sua senha no Sphynx: \n http://localhost:3000/password-recovery/" + existingUser.getId() + "/" + randomHash
        );   
    }

    public void passwordReset(Long id, String hash, String newPassword) {
        RecoveryHash recoveryHash = recoveryHashRepository.findByUserIdAndHashAndIsValid(id, hash, true);

        User user = recoveryHash.getUser();
        user.setPassword(passwordEncoder.encode(newPassword));
        repository.save(user);

        recoveryHash.setValid(false);
        recoveryHashRepository.save(recoveryHash);
    }
}
