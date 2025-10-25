CREATE TABLE week_days_groups (
    id SERIAL PRIMARY KEY,
    group_id BIGINT NOT NULL,
    week_day_id BIGINT NOT NULL,
    dtcreate TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    dtupdate TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    FOREIGN KEY (group_id) REFERENCES permission_groups(id) ON DELETE CASCADE,
    FOREIGN KEY (week_day_id) REFERENCES week_days(id) ON DELETE CASCADE
);