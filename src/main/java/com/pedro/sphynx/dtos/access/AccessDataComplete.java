package com.pedro.sphynx.dtos.access;

import com.pedro.sphynx.dtos.consumer.ConsumerDataComplete;
import com.pedro.sphynx.dtos.local.LocalDataComplete;
import com.pedro.sphynx.entities.Access;

import java.time.LocalDateTime;

public record AccessDataComplete(Long id, Boolean status, ConsumerDataComplete consumer, LocalDataComplete local, LocalDateTime date) {
    public AccessDataComplete(Access data){
        this(data.getId(), data.getStatus(),new ConsumerDataComplete(data.getConsumer()), new LocalDataComplete(data.getLocal()), data.getDate());
    }
}
