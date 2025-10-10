create table if not exists units(
    id BIGSERIAL PRIMARY KEY,
    name varchar(255) not null UNIQUE,
    dtcreate TIMESTAMP  not null,
    dtupdate TIMESTAMP
);