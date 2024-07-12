package com.pedro.sphynx.infrastructure.repository;

import com.pedro.sphynx.infrastructure.entities.Local;
import com.pedro.sphynx.infrastructure.entities.LocalGroup;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.swing.*;
import java.util.List;

public interface LocalGroupRepository extends JpaRepository<LocalGroup, Long> {

    LocalGroup getReferenceByLocalMac(String mac);

    List<LocalGroup> findAllByLocalMac(String macFormatted);
}
