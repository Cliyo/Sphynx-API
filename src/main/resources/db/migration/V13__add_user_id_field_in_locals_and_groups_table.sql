ALTER TABLE locals ADD COLUMN user_id BIGINT;
ALTER TABLE groups ADD COLUMN user_id BIGINT;

ALTER TABLE locals ADD CONSTRAINT fk_locals_user FOREIGN KEY (user_id) REFERENCES users(id);
ALTER TABLE groups ADD CONSTRAINT fk_groups_user FOREIGN KEY (user_id) REFERENCES users(id);