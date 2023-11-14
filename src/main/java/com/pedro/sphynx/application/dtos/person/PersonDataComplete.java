package com.pedro.sphynx.application.dtos.person;

import com.pedro.sphynx.infrastructure.entities.Person;

public record PersonDataComplete(Long id, String ra, String name, String gender) {
    public PersonDataComplete(Person data){
        this(data.getId(), data.getRa(), data.getName(), data.getGender());
    }
}
