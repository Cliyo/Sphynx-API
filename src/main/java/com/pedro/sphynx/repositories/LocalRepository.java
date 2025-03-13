package com.pedro.sphynx.repositories;

import com.pedro.sphynx.entities.Local;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LocalRepository extends JpaRepository<Local, Long> {
    boolean existsByName(String local);

    Local findByName(String local);

    Local findByMac(String mac);

    Local getReferenceByName(String name);

    boolean existsByMac(String mac);

    void deleteByName(String name);
}
