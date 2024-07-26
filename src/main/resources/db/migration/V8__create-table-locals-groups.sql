create table if not exists locals_groups(
    id bigint not null auto_increment,
    local_id bigint not null,
    group_id bigint not null,

    primary key(id),
    constraint fk_permissions_local_id foreign key (local_id) references locals(id) ON DELETE CASCADE,
    constraint fk_permissionsConsumer_group_id foreign key (group_id) references permission_groups(id)
);