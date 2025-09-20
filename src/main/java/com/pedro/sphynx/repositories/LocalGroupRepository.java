package com.pedro.sphynx.repositories;

import com.pedro.sphynx.entities.Local;
import com.pedro.sphynx.entities.LocalGroup;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LocalGroupRepository extends JpaRepository<LocalGroup, Long> {

    LocalGroup getReferenceByLocalMac(String mac);

    List<LocalGroup> findAllByLocalMac(String macFormatted);

    List<LocalGroup> findByLocal(Local local);
}
