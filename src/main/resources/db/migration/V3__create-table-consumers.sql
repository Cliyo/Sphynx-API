create table if not exists consumers(
    id bigint not null auto_increment,
    person_id bigint not null,
    tag varchar(255) not null,

    primary key(id),
    constraint fk_schedules_person_id foreign key (person_id) references persons(id)
);