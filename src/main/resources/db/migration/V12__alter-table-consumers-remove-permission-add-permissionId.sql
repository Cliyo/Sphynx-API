alter table consumers drop column permission;

alter table consumers add column permission_id bigint;
alter table consumers add constraint fk_consumer_permission foreign key (permission_id) references permissions(level);