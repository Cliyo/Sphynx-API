package com.pedro.sphynx.dtos.local;

import com.pedro.sphynx.dtos.group.GroupDataComplete;
import com.pedro.sphynx.entities.Local;

import java.time.LocalDateTime;
import java.util.List;

public record LocalDataComplete(Long id, String name, String mac, LocalDateTime dtcreate, LocalDateTime dtupdate, List<GroupDataComplete> groups) {
    public LocalDataComplete(Local data){
        this(data.getId(), data.getName(), data.getMac(), data.getDtcreate(), data.getDtupdate(), data.getGroups().stream().map(GroupDataComplete::new).toList());
    }
}