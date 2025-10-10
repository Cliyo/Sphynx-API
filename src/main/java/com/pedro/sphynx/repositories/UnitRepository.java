package com.pedro.sphynx.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pedro.sphynx.entities.Unit;

public interface UnitRepository extends JpaRepository<Unit, Long> {
    
}
