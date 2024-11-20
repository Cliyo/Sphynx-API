package com.pedro.sphynx.infrastructure.repository;

import com.pedro.sphynx.infrastructure.entities.Consumer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ConsumerRepository extends JpaRepository<Consumer, Long> {

    boolean existsByTag(String tag);

    Consumer findByTag(String ra);

    boolean existsByRa(String ra);

    Consumer getReferenceByRa(String ra);

    void deleteByRa(String ra);

    List<Consumer> findAllByGroupName(String s);

    List<Consumer> findAllByTag(String tag);

    boolean existsByFingerprint(byte[] fingerprint);

    List<Consumer> findAllByFingerprint(byte[] fingerprint);

    Consumer findByFingerprint(byte[] fingerprint);

}
