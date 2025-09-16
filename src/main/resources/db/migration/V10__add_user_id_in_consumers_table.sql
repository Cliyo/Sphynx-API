alter table consumers add user_id bigint;
alter table consumers add constraint fk_user foreign key (user_id) references users(id);