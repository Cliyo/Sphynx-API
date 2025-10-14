package com.pedro.sphynx.repositories;

import com.pedro.sphynx.entities.Local;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface LocalRepository extends JpaRepository<Local, Long> {
    boolean existsByNameAndUnitId(String local, Long unitId);

    Local findByNameAndUnitId(String local, Long unitId);

    Local findByMacAndUnitId(String mac, Long unitId);

    Local findByIdAndUnitId(Long id, Long unitId);

    Local getReferenceByNameAndUnitId(String name, Long unitId);

    boolean existsByMacAndUnitId(String mac, Long unitId);

    void deleteByNameAndUnitId(String name, Long unitId);

    List<Local> findAllByUserId(Long id);

    List<Local> findAllByUnitId(Long id);

    List<Local> findAllByMac(String mac);

    boolean existsByIdAndUnitId(Long id, Long unitId);

}
