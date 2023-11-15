package com.pedro.sphynx.application.dtos.access;

import com.pedro.sphynx.infrastructure.entities.Access;

import java.time.LocalDateTime;

public record AccessDataComplete(Long id, String ra, String tag, String name, String local, LocalDateTime date) {
    public AccessDataComplete(Access data){
        this(data.getId(), data.getRa(), data.getTag(), null, data.getLocal(), data.getDate());
    }

    public AccessDataComplete withName(AccessDataComplete access, String name){
        return new AccessDataComplete(access.id, access.ra, access.tag, name, access.local, access.date);
    }
}
