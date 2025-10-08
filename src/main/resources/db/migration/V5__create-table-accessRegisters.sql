create table if not exists access_registers(
    id BIGSERIAL PRIMARY KEY,
    consumer_id bigint not null,
    local_id bigint not null,
    dtcreate TIMESTAMP  not null,

    constraint fk_access_consumer_id foreign key (consumer_id) references consumers(id) on delete cascade on update cascade,
    constraint fk_access_local_id foreign key (local_id) references locals(id) on delete cascade on update cascade
);