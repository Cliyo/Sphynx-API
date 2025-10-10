package com.pedro.sphynx.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pedro.sphynx.entities.PermissionMenu;

public interface PermissionMenuRepository extends JpaRepository<PermissionMenu, Long> {
    List<PermissionMenu> findByNameIn(List<String> names);
}
