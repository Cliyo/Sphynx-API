package com.pedro.sphynx.repositories;

import com.pedro.sphynx.entities.Consumer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ConsumerRepository extends JpaRepository<Consumer, Long> {

    List<Consumer> findAllByRaIsLike(String ra);

    boolean existsByTag(String tag);

    Consumer findByTag(String ra);

    boolean existsByRa(String ra);

    boolean existsByFingerprint(long fingerprint);

    List<Consumer> findAllByFingerprint(long fingerprint);

    List<Consumer> findAllByUserId(Long userId);

    List<Consumer> findAllByRaIsLikeAndUserId(String ra, Long userId);

    Consumer findByFingerprint(long fingerprint);

    @Query("SELECT COUNT(c) > 0 FROM Consumer c WHERE c.ra = :ra AND c.id <> :excludeId")
    boolean existsByRaAndIdNot(@Param("ra") String ra, @Param("excludeId") Long excludeId);

    @Query("SELECT COUNT(c) > 0 FROM Consumer c WHERE c.tag = :tag AND c.id <> :excludeId")
    boolean existsByTagAndIdNot(@Param("tag") String tag, @Param("excludeId") Long excludeId);

    @Query("SELECT COUNT(c) > 0 FROM Consumer c WHERE c.fingerprint = :fingerprint AND c.id <> :excludeId")
    boolean existsByFingerAndIdNot(@Param("fingerprint") long fingerprint, @Param("excludeId") Long excludeId);

}
