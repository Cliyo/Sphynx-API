package com.pedro.sphynx.infrastructure.repository;

import com.pedro.sphynx.infrastructure.entities.Group;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface GroupRepository extends JpaRepository<Group, Integer> {
    boolean existsByName(String name);

    Group getReferenceByName(String permission);
}
