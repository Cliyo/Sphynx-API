create table if not exists permissions(
    level bigint not null unique,
    name varchar(255) not null,
    dtcreate datetime not null,
    dtupdate datetime
);