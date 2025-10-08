ALTER TABLE access_registers DROP COLUMN consumer_id;
ALTER TABLE access_registers ADD COLUMN user_id bigint;
ALTER TABLE access_registers ADD CONSTRAINT fk_access_user_id FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE ON UPDATE CASCADE;

DROP TABLE IF EXISTS consumers;