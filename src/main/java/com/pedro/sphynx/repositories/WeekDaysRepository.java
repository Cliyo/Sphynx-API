package com.pedro.sphynx.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pedro.sphynx.entities.WeekDay;

public interface WeekDaysRepository extends JpaRepository<WeekDay, Long> {
    List<WeekDay> findByNameIn(List<String> names);
}
