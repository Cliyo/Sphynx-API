package com.pedro.sphynx.dtos.group;

import java.util.Set;

import com.pedro.sphynx.entities.Group;
import com.pedro.sphynx.entities.WeekDay;

public record GroupDataComplete(Long id, String name, Set<WeekDay>WeekDays) {
    public GroupDataComplete(Group data){
        this(
            data.getId(),
            data.getName(),
            data.getWeekDays()
        );
    }
}
