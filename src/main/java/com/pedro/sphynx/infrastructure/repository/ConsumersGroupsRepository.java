package com.pedro.sphynx.infrastructure.repository;

import com.pedro.sphynx.infrastructure.entities.Group;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConsumersGroupsRepository extends JpaRepository<Group, Long> {
}
