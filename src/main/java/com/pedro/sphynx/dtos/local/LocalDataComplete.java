package com.pedro.sphynx.dtos.local;

import com.pedro.sphynx.entities.Local;

import java.time.LocalDateTime;

public record LocalDataComplete(Long id, String name, String mac, LocalDateTime dtcreate, LocalDateTime dtupdate) {
    public LocalDataComplete(Local data){
        this(data.getId(), data.getName(), data.getMac(), data.getDtcreate(), data.getDtupdate());
    }
}