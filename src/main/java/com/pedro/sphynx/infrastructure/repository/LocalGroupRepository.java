package com.pedro.sphynx.infrastructure.repository;

import com.pedro.sphynx.infrastructure.entities.LocalGroup;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.swing.*;

public interface LocalGroupRepository extends JpaRepository<LocalGroup, Long> {
    LocalGroup getReferenceByLocalName(String tag);

    LocalGroup getReferenceByLocalMac(String mac);
}
