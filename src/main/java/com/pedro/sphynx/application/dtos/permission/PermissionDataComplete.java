package com.pedro.sphynx.application.dtos.permission;

import com.pedro.sphynx.infrastructure.entities.Group;

public record PermissionDataComplete(String name) {
    public PermissionDataComplete(Group data){
        this(data.getName());
    }
}
