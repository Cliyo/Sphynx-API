create table if not exists consumers(
    id bigint not null auto_increment,
    ra varchar(255) not null,
    tag varchar(255) not null,

    primary key(id)
);