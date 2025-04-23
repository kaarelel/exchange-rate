-- This script runs automatically in Docker via Postgres image if mounted correctly
-- Create roles or seed initial data here

CREATE TABLE IF NOT EXISTS users (
                                     id SERIAL PRIMARY KEY,
                                     username VARCHAR(100) UNIQUE NOT NULL,
                                     password TEXT NOT NULL,
                                     role VARCHAR(50) NOT NULL
);

INSERT INTO users (username, password, role) VALUES
                                                 ('viewer', '$2a$10$G9gU7/9h5/h9aFJXy4XgfuZJgPnsjkA1ShGyJ8HqQQo0F.qRUEozm', 'VIEWER'), -- password: viewerpass
                                                 ('admin', '$2a$10$G9gU7/9h5/h9aFJXy4XgfuZJgPnsjkA1ShGyJ8HqQQo0F.qRUEozm', 'ADMIN'); -- password: viewerpass