package com.pedro.sphynx.application.dtos.consumer;

import com.pedro.sphynx.infrastructure.entities.Consumer;

public record ConsumerDataComplete (Long id, String ra, String tag){
    public ConsumerDataComplete(Consumer consumer){
        this(consumer.getId(), consumer.getRa(), consumer.getTag());
    }
}
