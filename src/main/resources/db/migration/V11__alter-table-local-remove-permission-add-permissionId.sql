alter table locals drop column permission;

alter table locals add column permission_id bigint;
alter table locals add constraint fk_permission foreign key (permission_id) references permissions(level);