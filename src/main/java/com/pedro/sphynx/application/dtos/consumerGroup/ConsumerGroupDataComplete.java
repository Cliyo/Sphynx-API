package com.pedro.sphynx.application.dtos.consumerGroup;

import com.pedro.sphynx.application.dtos.consumer.ConsumerDataComplete;
import com.pedro.sphynx.application.dtos.group.GroupDataComplete;
import com.pedro.sphynx.infrastructure.entities.ConsumerGroup;

public record ConsumerGroupDataComplete(Long id, ConsumerDataComplete consumer, GroupDataComplete group) {
    public ConsumerGroupDataComplete(ConsumerGroup data){
        this(data.getId(), new ConsumerDataComplete(data.getConsumer()), new GroupDataComplete(data.getGroup()));
    }
}
