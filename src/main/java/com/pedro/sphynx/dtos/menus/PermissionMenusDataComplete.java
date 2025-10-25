package com.pedro.sphynx.dtos.menus;

import com.pedro.sphynx.entities.PermissionMenu;

public record PermissionMenusDataComplete (Long id, String name) {
    public PermissionMenusDataComplete (PermissionMenu data){
        this(
            data.getId(),
            data.getName()
        );
    }
}
