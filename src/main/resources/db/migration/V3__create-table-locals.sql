create table if not exists locals(
    id bigint not null auto_increment,
    name varchar(255) not null,
    mac varchar(255) not null UNIQUE,
    dtcreate datetime not null,
    dtupdate datetime,

    primary key(id)
);