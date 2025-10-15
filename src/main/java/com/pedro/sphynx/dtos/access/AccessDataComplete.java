package com.pedro.sphynx.dtos.access;

import com.pedro.sphynx.dtos.auth.UserDataComplete;
import com.pedro.sphynx.dtos.local.LocalDataComplete;
import com.pedro.sphynx.dtos.unit.UnitDataComplete;
import com.pedro.sphynx.entities.Access;

import java.time.LocalDate;
import java.time.LocalTime;

public record AccessDataComplete(Long id, Boolean status, UserDataComplete user, LocalDataComplete local, UnitDataComplete unit, LocalDate date, LocalTime time) {
    public AccessDataComplete(Access data){
        this(data.getId(), data.getStatus(),new UserDataComplete(data.getUser()), new LocalDataComplete(data.getLocal()), new UnitDataComplete(data.getUnit()), data.getDate().toLocalDate(), data.getDate().toLocalTime());
    }
}
