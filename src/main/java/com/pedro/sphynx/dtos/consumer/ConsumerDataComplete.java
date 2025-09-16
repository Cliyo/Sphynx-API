package com.pedro.sphynx.dtos.consumer;

import com.pedro.sphynx.dtos.group.GroupDataComplete;
import com.pedro.sphynx.dtos.auth.UserDataComplete;
import com.pedro.sphynx.entities.Consumer;

public record ConsumerDataComplete (Long id, String name, String ra, String tag, GroupDataComplete group, UserDataComplete user, long fingerprint){

    public ConsumerDataComplete(Consumer consumer){
        this(consumer.getId(), consumer.getName(), consumer.getRa(), consumer.getTag(), new GroupDataComplete(consumer.getGroup()), new UserDataComplete(consumer.getUser()), consumer.getFingerprint());
    }
}
