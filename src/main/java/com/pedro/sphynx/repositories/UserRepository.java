package com.pedro.sphynx.repositories;

import com.pedro.sphynx.entities.User;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.userdetails.UserDetails;

public interface UserRepository extends JpaRepository<User, Long> {
    UserDetails findByUser(String user);

    Boolean existsByUser(String user);

    boolean existsByRa(String ra);

    List<User> findAllByRaIsLike(String ra);

    boolean existsByTag(String tag);

    User findByTag(String ra);

    boolean existsByFingerprint(long fingerprint);

    List<User> findAllByFingerprint(long fingerprint);

    List<User> findAllByUserCreatorId(Long userCreatorId);

    List<User> findAllByRaIsLikeAndUserCreatorId(String ra, Long userCreatorId);

    User findByFingerprint(long fingerprint);

    @Query("SELECT COUNT(c) > 0 FROM User c WHERE c.ra = :ra AND c.id <> :excludeId")
    boolean existsByRaAndIdNot(@Param("ra") String ra, @Param("excludeId") Long excludeId);

    @Query("SELECT COUNT(c) > 0 FROM User c WHERE c.tag = :tag AND c.id <> :excludeId")
    boolean existsByTagAndIdNot(@Param("tag") String tag, @Param("excludeId") Long excludeId);

    @Query("SELECT COUNT(c) > 0 FROM User c WHERE c.fingerprint = :fingerprint AND c.id <> :excludeId")
    boolean existsByFingerAndIdNot(@Param("fingerprint") long fingerprint, @Param("excludeId") Long excludeId);
}
