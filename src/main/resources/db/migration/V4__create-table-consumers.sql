create table if not exists consumers(
    id bigint not null auto_increment,
    name varchar(255) not null,
    ra varchar(255) not null,
    tag varchar(255) not null,
    group_id bigint not null,
    dtcreate datetime not null,
    dtupdate datetime,

    primary key(id),
    constraint fk_group_consumer_id foreign key (group_id) references permission_groups(id)
);