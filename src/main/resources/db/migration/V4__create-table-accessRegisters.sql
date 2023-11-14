create table accessRegisters(
    id bigint not null auto_increment,
    ra varchar(255) not null,
    local varchar(255) not null,
    dtcreate datetime not null,

    primary key(id)
);