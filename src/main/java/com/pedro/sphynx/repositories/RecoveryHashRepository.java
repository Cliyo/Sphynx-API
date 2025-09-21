package com.pedro.sphynx.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pedro.sphynx.entities.RecoveryHash;

public interface RecoveryHashRepository extends JpaRepository<RecoveryHash, Long> {

    RecoveryHash findByUserIdAndHashAndIsValid(Long id, String hash, boolean b);
    
}
