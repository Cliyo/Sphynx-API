create table if not exists users(
    id BIGSERIAL PRIMARY KEY,
    "user" varchar(255) not null,
    password varchar(255) not null
);