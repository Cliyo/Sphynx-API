package com.pedro.sphynx.infrastructure.repository;

import com.pedro.sphynx.infrastructure.entities.Consumer;
import com.pedro.sphynx.infrastructure.entities.ConsumerGroup;
import com.pedro.sphynx.infrastructure.entities.Group;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;

public interface ConsumerGroupRepository extends JpaRepository<ConsumerGroup, Long> {

    List<ConsumerGroup> findAllByGroupName(String s);
}
