package com.pedro.sphynx.application.dtos.group;

import com.pedro.sphynx.infrastructure.entities.Group;

public record GroupDataComplete(Long id, String name) {
    public GroupDataComplete(Group data){
        this(data.getId(),data.getName());
    }
}
