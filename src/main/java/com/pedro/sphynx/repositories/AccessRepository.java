package com.pedro.sphynx.repositories;

import com.pedro.sphynx.entities.Access;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface AccessRepository extends JpaRepository<Access, Long> {
    
    @Query("SELECT a FROM Access a ORDER BY a.date DESC")
    List<Access> findAll();

    List<Access> findAllByConsumerRa(String ra);

    List<Access> findAllByLocalName(String local);

    List<Access> findAllByDateBetween(LocalDateTime from, LocalDateTime to);

    List<Access> findAllByConsumer_RaAndLocal_NameAndDateBetween(String ra, String local, LocalDateTime from, LocalDateTime to);
}
