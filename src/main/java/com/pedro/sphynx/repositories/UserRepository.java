package com.pedro.sphynx.repositories;

import com.pedro.sphynx.entities.User;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

public interface UserRepository extends JpaRepository<User, Long> {
    UserDetails findByUser(String user);

    Boolean existsByUser(String user);

    boolean existsByRa(String ra);
}
