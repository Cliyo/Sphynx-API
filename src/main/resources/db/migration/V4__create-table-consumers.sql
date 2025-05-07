create table if not exists consumers(
    id BIGSERIAL PRIMARY KEY,
    name varchar(255) not null,
    ra varchar(255) UNIQUE,
    tag varchar(255) not null UNIQUE,
    group_id bigint not null,
    dtcreate TIMESTAMP  not null,
    dtupdate TIMESTAMP,
    CONSTRAINT fk_group_consumer_id
    FOREIGN KEY (group_id)
    REFERENCES permission_groups(id)
    ON DELETE CASCADE
    ON UPDATE CASCADE
);