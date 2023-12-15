package com.pedro.sphynx.infrastructure.repository;

import com.pedro.sphynx.infrastructure.entities.Local;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LocalRepository extends JpaRepository<Local, Long> {
    boolean existsByName(String local);

    Local findByName(String local);

    Local getReferenceByName(String name);

    boolean existsByMac(String mac);

    void deleteByName(String name);
}
