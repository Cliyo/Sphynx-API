package com.pedro.sphynx.application.dtos.consumer;

import com.pedro.sphynx.application.dtos.person.PersonDataComplete;
import com.pedro.sphynx.infrastructure.entities.Consumer;

public record ConsumerDataComplete (Long id, PersonDataComplete person, String tag, int permission){

    public ConsumerDataComplete(Consumer consumer){
        this(consumer.getId(), new PersonDataComplete(consumer.getPerson()), consumer.getTag(), consumer.getPermission());
    }
}
