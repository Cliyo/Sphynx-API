package com.pedro.sphynx.infrastructure.repository;

import com.pedro.sphynx.infrastructure.entities.Access;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AccessRepository extends JpaRepository<Access, Long> {
    Access findOneByRa(String ra);

    List<Access> findAllByRa(String ra);

    List<Access> findAllByLocal(String local);
}
