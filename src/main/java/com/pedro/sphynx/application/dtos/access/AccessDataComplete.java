package com.pedro.sphynx.application.dtos.access;

import com.pedro.sphynx.application.dtos.consumer.ConsumerDataComplete;
import com.pedro.sphynx.application.dtos.local.LocalDataComplete;
import com.pedro.sphynx.infrastructure.entities.Access;

import java.time.LocalDateTime;

public record AccessDataComplete(Long id, Boolean status, ConsumerDataComplete consumer, LocalDataComplete local, LocalDateTime date) {
    public AccessDataComplete(Access data){
        this(data.getId(), data.getStatus(),new ConsumerDataComplete(data.getConsumer()), new LocalDataComplete(data.getLocal()), data.getDate());
    }
}
