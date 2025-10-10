package com.pedro.sphynx.dtos.unit;

import com.pedro.sphynx.entities.Group;

public record UnitDataComplete(Long id, String name) {
    public UnitDataComplete(Group data){
        this(data.getId(),data.getName());
    }
}
