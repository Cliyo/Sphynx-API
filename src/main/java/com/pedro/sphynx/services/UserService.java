package com.pedro.sphynx.services;

import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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

    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder(12);

    public UserDataComplete create(UserDataRegisterInput data) {
        if (userRepository.existsByUser(data.user())) {
            throw new EntityExistsException("User already exists");
        }

        if (userRepository.existsByRa(data.ra())) {
            throw new EntityExistsException("RA already exists");
        }

        String encryptedPassword = passwordEncoder.encode(data.password());
        var user = userRepository.save(new User(null, data.name(), data.ra(), data.isAdmin(), data.user(), encryptedPassword));
        return new UserDataComplete(user);
    }

    public UserDataComplete getById(Long id) {
        User userEntity = userRepository.findById(id)
            .orElseThrow(() -> new EntityExistsException("User not found"));
        return new UserDataComplete(userEntity);
    }

    public List<UserDataComplete> getAll() {
        List<User> users = userRepository.findAll();
        return users
            .stream()
            .map(UserDataComplete::new)
            .sorted(Comparator.comparing(UserDataComplete::id).reversed())
            .toList();
    }

    public void delete(Long id) {
        if (!userRepository.existsById(id)) {
            throw new EntityExistsException("User not found");
        }

        userRepository.deleteById(id);
    }
    
}
