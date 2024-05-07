package com.pedro.sphynx.infrastructure.repository;

import com.pedro.sphynx.infrastructure.entities.Permission;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PermissionRepository extends JpaRepository<Permission, Integer> {
}
