create table if not exists consumers_groups(
    id bigint not null auto_increment,
    consumer_id bigint not null,
    group_id bigint not null,

    primary key(id),
    constraint fk_permissions_consumer_id foreign key (consumer_id) references consumers(id),
    constraint fk_permissions_group_id foreign key (group_id) references permissionGroups(id)
);