package com.pedro.sphynx.services;

import com.pedro.sphynx.dtos.auth.UserDataVerifyInput;
import com.pedro.sphynx.dtos.auth.UserDataVerifyOutput;
import com.pedro.sphynx.entities.User;
import com.pedro.sphynx.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AuthService implements UserDetailsService {

    @Autowired
    private UserRepository repository;

    @Autowired
    private TokenService tokenService;

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
}
