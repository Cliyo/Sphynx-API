package com.pedro.sphynx.application.dtos.localGroup;

import com.pedro.sphynx.application.dtos.group.GroupDataComplete;
import com.pedro.sphynx.application.dtos.local.LocalDataComplete;
import com.pedro.sphynx.infrastructure.entities.LocalGroup;

public record LocalGroupDataComplete(Long id, LocalDataComplete local, GroupDataComplete group) {
    public LocalGroupDataComplete(LocalGroup data){
        this(data.getId(), new LocalDataComplete(data.getLocal()), new GroupDataComplete(data.getGroup()));
    }
}
