package com.pedro.sphynx.infrastructure.repository;

import com.pedro.sphynx.infrastructure.entities.Permission;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PermissionRepository extends JpaRepository<Permission, Integer> {
    void deleteByLevel(Integer level);

    boolean existsByLevel(int level);

    boolean existsByName(String name);

    Permission getReferenceByLevel(int permission);
}
