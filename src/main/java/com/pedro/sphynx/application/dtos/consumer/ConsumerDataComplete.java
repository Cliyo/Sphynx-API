package com.pedro.sphynx.application.dtos.consumer;

import com.pedro.sphynx.infrastructure.entities.Consumer;

public record ConsumerDataComplete (Long id, String ra, String name, String tag){

    public ConsumerDataComplete(Consumer consumer){
        this(consumer.getId(), consumer.getRa(), null, consumer.getTag());
    }

    public ConsumerDataComplete withName(ConsumerDataComplete consumer, String name){
        return new ConsumerDataComplete(consumer.id, consumer.ra, name, consumer.tag);
    }
}
