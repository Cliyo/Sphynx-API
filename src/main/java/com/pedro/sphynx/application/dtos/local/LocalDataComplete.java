package com.pedro.sphynx.application.dtos.local;

import com.pedro.sphynx.infrastructure.entities.Group;
import com.pedro.sphynx.infrastructure.entities.Local;

import java.time.LocalDateTime;
import java.util.List;

public record LocalDataComplete(Long id, String name, String mac, LocalDateTime dtcreate, LocalDateTime dtupdate) {
    public LocalDataComplete(Local data){
        this(data.getId(), data.getName(), data.getMac(), data.getDtcreate(), data.getDtupdate());
    }
}