create table if not exists locals(
    id BIGSERIAL PRIMARY KEY,
    name varchar(255) not null UNIQUE,
    mac varchar(255) not null UNIQUE,
    dtcreate TIMESTAMP  not null,
    dtupdate TIMESTAMP
    );