create table if not exists permissions(
    id bigint not null auto_increment,
    name varchar(255) not null,
    dtcreate datetime not null,
    dtupdate datetime
);