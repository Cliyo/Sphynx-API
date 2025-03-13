package com.pedro.sphynx.repositories;

import com.pedro.sphynx.entities.Group;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GroupRepository extends JpaRepository<Group, Integer> {
    boolean existsByName(String name);

    Group getReferenceByName(String permission);
}
