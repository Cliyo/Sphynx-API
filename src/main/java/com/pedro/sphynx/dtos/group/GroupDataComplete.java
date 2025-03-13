package com.pedro.sphynx.dtos.group;

import com.pedro.sphynx.entities.Group;

public record GroupDataComplete(Long id, String name) {
    public GroupDataComplete(Group data){
        this(data.getId(),data.getName());
    }
}
