package com.pedro.sphynx.repositories;

import com.pedro.sphynx.entities.Group;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface GroupRepository extends JpaRepository<Group, Integer> {
    boolean existsByName(String name);

    @Query("SELECT g FROM Group g WHERE g.name LIKE %:name%")
    List<Group> findAllByNameContaining(@Param("name") String name);

    Group getReferenceByName(String permission);
}
