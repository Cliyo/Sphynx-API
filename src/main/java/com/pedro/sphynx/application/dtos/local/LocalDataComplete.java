package com.pedro.sphynx.application.dtos.local;

import com.pedro.sphynx.infrastructure.entities.Local;

import java.time.LocalDateTime;

public record LocalDataComplete(Long id, String name, String mac, int permission, LocalDateTime dtcreate, LocalDateTime dtupdate) {
    public LocalDataComplete(Local data){
        this(data.getId(), data.getName(), data.getMac(), data.getPermission(), data.getDtcreate(), data.getDtupdate());
    }
}
