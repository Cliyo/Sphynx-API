CREATE TABLE permission_menus (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

INSERT INTO permission_menus (name) VALUES ('DASHBOARD');
INSERT INTO permission_menus (name) VALUES ('ACCESS');
INSERT INTO permission_menus (name) VALUES ('USERS');
INSERT INTO permission_menus (name) VALUES ('DEPENDENTS');
INSERT INTO permission_menus (name) VALUES ('GROUPS');
INSERT INTO permission_menus (name) VALUES ('LOCALS');
