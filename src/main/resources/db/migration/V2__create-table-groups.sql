create table if not exists permission_groups(
    id BIGSERIAL PRIMARY KEY,
    name varchar(255) not null,
    dtcreate TIMESTAMP  not null,
    dtupdate TIMESTAMP
    );