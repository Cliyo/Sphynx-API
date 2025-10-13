package com.pedro.sphynx.dtos.unit;

import com.pedro.sphynx.entities.Unit;

public record UnitDataComplete(Long id, String name) {
    public UnitDataComplete(Unit data){
        this(data.getId(),data.getName());
    }
}
