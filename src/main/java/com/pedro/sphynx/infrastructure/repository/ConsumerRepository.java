package com.pedro.sphynx.infrastructure.repository;

import com.pedro.sphynx.infrastructure.entities.Consumer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConsumerRepository extends JpaRepository<Consumer, Long> {
    boolean existsByPersonRa(String ra);

    Consumer getReferenceByPersonRa(String ra);

    void deleteByPersonRa(String ra);

    boolean existsByTag(String tag);

    Consumer findByTag(String ra);
}
