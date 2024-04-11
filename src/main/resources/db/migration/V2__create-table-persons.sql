create table if not exists persons(
    id bigint not null auto_increment,
    ra varchar(255) not null,
    name varchar(255) not null,
    gender varchar(255) not null,

    primary key(id)
);