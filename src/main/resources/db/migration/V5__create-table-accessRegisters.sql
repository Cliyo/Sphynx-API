create table if not exists accessregisters(
    id bigint not null auto_increment,
    consumer_id bigint not null,
    local_id bigint not null,
    dtcreate datetime not null,

    primary key(id),
    constraint fk_schedules_consumer_id foreign key (consumer_id) references consumers(id),
    constraint fk_schedules_local_id foreign key (local_id) references locals(id)
);