package com.pedro.sphynx.infrastructure.repository;

import com.pedro.sphynx.infrastructure.entities.Access;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface AccessRepository extends JpaRepository<Access, Long> {

    List<Access> findAllByConsumerRa(String ra);

    List<Access> findAllByLocalName(String local);

    List<Access> findAllByDateBetween(LocalDateTime from, LocalDateTime to);
}
