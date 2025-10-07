CREATE TABLE permission_menus_users (
    id SERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL,
    permission_menu_id BIGINT NOT NULL,
    dtcreate TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    dtupdate TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    FOREIGN KEY (permission_menu_id) REFERENCES permission_menus(id) ON DELETE CASCADE
);