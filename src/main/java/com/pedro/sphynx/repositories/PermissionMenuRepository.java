package com.pedro.sphynx.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pedro.sphynx.entities.PermissionMenu;
import com.pedro.sphynx.utils.enums.PermissionMenuEnum;

public interface PermissionMenuRepository extends JpaRepository<PermissionMenu, Long> {
    List<PermissionMenu> findByNameIn(List<PermissionMenuEnum> names);
}
