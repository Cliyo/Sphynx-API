package com.pedro.sphynx.application.dtos.access;

import com.pedro.sphynx.infrastructure.entities.Access;

import java.time.LocalDateTime;

public record AccessDataComplete(Long id, String ra, String name, String local, LocalDateTime date) {
    public AccessDataComplete(Access data){
        this(data.getId(), data.getRa(), null, data.getLocal(), data.getDate());
    }

    public AccessDataComplete withName(AccessDataComplete access, String name){
        return new AccessDataComplete(access.id, access.ra, name, access.local, access.date);
    }
}
