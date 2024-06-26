create table if not exists consumers(
    id bigint not null auto_increment,
    name varchar(255) not null,
    ra varchar(255) not null,
    tag varchar(255) not null,
    dtcreate datetime not null,
    dtupdate datetime,

    primary key(id)
);