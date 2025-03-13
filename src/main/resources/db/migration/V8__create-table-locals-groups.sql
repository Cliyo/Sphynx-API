create table if not exists locals_groups(
    id BIGSERIAL PRIMARY KEY,
    local_id bigint not null,
    group_id bigint not null,

    constraint fk_permissions_local_id foreign key (local_id) references locals(id) on delete cascade on update cascade,
    constraint fk_permissionsConsumer_group_id foreign key (group_id) references permission_groups(id) on delete cascade on update cascade
);