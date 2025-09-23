create table if not exists recovery_hashes(
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL,
    hash varchar(255) not null,
    is_valid boolean not null default true,
    dtcreate TIMESTAMP,
    dtupdate TIMESTAMP,

    constraint fk_user_recovery_hashes foreign key(user_id) references users(id)
);