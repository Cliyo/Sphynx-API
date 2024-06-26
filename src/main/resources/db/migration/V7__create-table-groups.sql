create table if not exists permissionGroups(
    id bigint not null auto_increment,
    name varchar(255) not null,
    dtcreate datetime not null,
    dtupdate datetime,

    primary key(id)
);