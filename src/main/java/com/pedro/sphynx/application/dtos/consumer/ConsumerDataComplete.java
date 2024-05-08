package com.pedro.sphynx.application.dtos.consumer;

import com.pedro.sphynx.application.dtos.permission.PermissionDataComplete;
import com.pedro.sphynx.infrastructure.entities.Consumer;
import com.pedro.sphynx.infrastructure.entities.Permission;

public record ConsumerDataComplete (Long id, String ra, String tag, PermissionDataComplete permission){

    public ConsumerDataComplete(Consumer consumer){
        this(consumer.getId(), consumer.getRa(), consumer.getTag(), new PermissionDataComplete(consumer.getPermission()));
    }
}
