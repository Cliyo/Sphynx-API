package com.pedro.sphynx.application.dtos.consumer;

import com.pedro.sphynx.application.dtos.group.GroupDataComplete;
import com.pedro.sphynx.infrastructure.entities.Consumer;

public record ConsumerDataComplete (Long id, String name, String ra, String tag, GroupDataComplete group, byte[] fingerprint) {

    public ConsumerDataComplete(Consumer consumer){
        this(consumer.getId(), consumer.getName(), consumer.getRa(), consumer.getTag(), new GroupDataComplete(consumer.getGroup()), consumer.getFingerprint());
    }
}
