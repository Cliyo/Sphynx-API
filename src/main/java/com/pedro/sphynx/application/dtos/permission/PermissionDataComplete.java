package com.pedro.sphynx.application.dtos.permission;

import com.pedro.sphynx.infrastructure.entities.Permission;

public record PermissionDataComplete(int level, String name) {
    public PermissionDataComplete(Permission data){
        this(data.getLevel(), data.getName());
    }
}
