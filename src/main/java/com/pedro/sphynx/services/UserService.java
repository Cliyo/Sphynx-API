package com.pedro.sphynx.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pedro.sphynx.dtos.auth.UserDataComplete;
import com.pedro.sphynx.dtos.auth.UserDataRegisterInput;
import com.pedro.sphynx.entities.User;
import com.pedro.sphynx.repositories.UserRepository;

import jakarta.persistence.EntityExistsException;

@Service()
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public UserDataComplete create(UserDataRegisterInput data) {
        if (userRepository.existsByUser(data.user())) {
            throw new EntityExistsException("User already exists");
        }

        if (userRepository.existsByRa(data.ra())) {
            throw new EntityExistsException("RA already exists");
        }

        var user = userRepository.save(new com.pedro.sphynx.entities.User(null, data.name(), data.ra(), data.isAdmin(), data.user(), data.password()));
        return new UserDataComplete(user);
    }

    public User getByUser(String user) {
        return (User) userRepository.findByUser(user);
    }

    public void delete(Long id) {
        if (!userRepository.existsById(id)) {
            throw new EntityExistsException("User not found");
        }

        userRepository.deleteById(id);
    }
    
}
