ALTER TABLE locals ADD COLUMN unit_id BIGINT;
ALTER TABLE locals ADD CONSTRAINT fk_unit FOREIGN KEY (unit_id) REFERENCES units(id);

ALTER TABLE permission_groups ADD COLUMN unit_id BIGINT;
ALTER TABLE permission_groups ADD CONSTRAINT fk_unit FOREIGN KEY (unit_id) REFERENCES units(id);

ALTER TABLE access_registers ADD COLUMN unit_id BIGINT;
ALTER TABLE access_registers ADD CONSTRAINT fk_unit FOREIGN KEY (unit_id) REFERENCES units(id);
