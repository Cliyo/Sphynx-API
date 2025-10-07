package com.pedro.sphynx.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pedro.sphynx.entities.PermissionMenu;

public interface PermissionMenuRepository extends JpaRepository<PermissionMenu, Long> {
    
}
