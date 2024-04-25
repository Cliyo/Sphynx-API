package com.pedro.sphynx.infrastructure.repository;

import com.pedro.sphynx.infrastructure.entities.Consumer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConsumerRepository extends JpaRepository<Consumer, Long> {

    boolean existsByTag(String tag);

    Consumer findByTag(String ra);

    boolean existsByRa(String ra);

    Consumer getReferenceByRa(String ra);

    void deleteByRa(String ra);
}
