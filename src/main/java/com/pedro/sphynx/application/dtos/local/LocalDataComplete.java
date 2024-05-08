package com.pedro.sphynx.application.dtos.local;

import com.pedro.sphynx.application.dtos.permission.PermissionDataComplete;
import com.pedro.sphynx.infrastructure.entities.Local;

import java.time.LocalDateTime;

public record LocalDataComplete(Long id, String name, String mac, PermissionDataComplete permission, LocalDateTime dtcreate, LocalDateTime dtupdate) {
    public LocalDataComplete(Local data){
        this(data.getId(), data.getName(), data.getMac(), new PermissionDataComplete(data.getPermission()), data.getDtcreate(), data.getDtupdate());
    }
}
