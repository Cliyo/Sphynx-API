package com.pedro.sphynx.infrastructure.repository;

import com.pedro.sphynx.infrastructure.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

public interface UserRepository extends JpaRepository<User, Long> {
    UserDetails findByUser(String user);

    Boolean existsByUser(String user);
}
